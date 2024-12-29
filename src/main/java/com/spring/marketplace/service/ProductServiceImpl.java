package com.spring.marketplace.service;

import com.spring.marketplace.dto.CreateProductDto;
import com.spring.marketplace.dto.ReadProductDto;
import com.spring.marketplace.dto.UpdateProductDto;
import com.spring.marketplace.exception.ApplicationException;
import com.spring.marketplace.model.Product;
import com.spring.marketplace.repository.ProductRepository;
import com.spring.marketplace.utils.enums.ErrorType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ConversionService conversionService;

    @Override
    @Transactional(readOnly = true)
    public List<ReadProductDto> getAllProducts(int pageNo, int pageSize) {
        return Optional.of(productRepository.findAll(PageRequest.of(pageNo, pageSize)))
                .filter((item)->(!item.isEmpty()))
                .map(page -> {
                    log.info("Find all the products from page {}", page.getNumber());
                    return page.map(product -> conversionService.convert(product, ReadProductDto.class)).stream().toList();
                })
                .orElseThrow(() -> {
                    log.error("No products found");
                    return new ApplicationException(ErrorType.NO_PRODUCTS_FOUND);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public ReadProductDto getProductById(UUID id) {
        ReadProductDto product = productRepository.findById(id)
                .map(item -> conversionService.convert(item, ReadProductDto.class))
                .orElseThrow(() -> {
                    log.error("Product with id {} not found", id);
                    return new ApplicationException(ErrorType.PRODUCT_NOT_FOUND);
                });
        log.info("Product found: {}", product);

        return product;
    }

    @Override
    @Transactional
    public ReadProductDto saveProduct(CreateProductDto product) {
        Product productEntity = productRepository.findBySku(product.getSku())
                .filter((item) -> {
                    log.error("Product with sku {} already exists", item.getSku());
                    throw new ApplicationException(ErrorType.UNIQUE_CONSTRAINT_EXCEPTION);
                }).orElseGet(() -> {
                    log.info("Product saved: {}", product);
                    return productRepository.save(conversionService.convert(product, Product.class));
                });

        return conversionService.convert(productEntity, ReadProductDto.class);
    }

    @Override
    @Transactional
    public void deleteProduct(UUID id) {
        productRepository.findById(id)
                .ifPresentOrElse(item -> productRepository.deleteById(item.getId()),
                        () -> {
                    log.error("Product with id {} was not deleted", id);
                    throw new ApplicationException(ErrorType.PRODUCT_NOT_FOUND);});
        log.info("Product with id {} deleted", id);
    }

    @Override
    @Transactional
    public ReadProductDto updateProduct(UpdateProductDto product) {
        Product productEntity = productRepository.findById(product.getId())
                .orElseThrow(() -> {
                    log.error("Product with id{} not found",product.getId());
                    return new ApplicationException(ErrorType.PRODUCT_NOT_FOUND);
                });

        productRepository.findBySkuAndIdNot(product.getSku(),productEntity.getId())
                        .ifPresentOrElse((item) -> {
                            log.error("Product with this sku {} already exists", item.getSku());
                            throw new ApplicationException(ErrorType.UNIQUE_CONSTRAINT_EXCEPTION);
                        },()-> setProductDetails(product,productEntity));

        log.info("Product updated: {}", product);
        return conversionService.convert(productRepository.save(productEntity), ReadProductDto.class);
    }

    private void setProductDetails(UpdateProductDto from, Product to) {
        to.setSku(from.getSku());
        to.setPrice(from.getPrice());
        to.setName(from.getName());
        to.setDescription(from.getDescription());
        to.setCategory(from.getCategory());
        to.setName(from.getName());

        if (!from.getQuantity().equals(to.getQuantity())) {
            to.setQuantity(from.getQuantity());
            to.setUpdatedAt(LocalDateTime.now());
        }
    }
}
