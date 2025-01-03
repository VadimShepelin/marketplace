package com.spring.marketplace.service;

import com.spring.marketplace.dto.CreateProductDto;
import com.spring.marketplace.dto.GetProductResponse;
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
    public List<GetProductResponse> getAllProducts(int pageNo, int pageSize) {
        return Optional.of(productRepository.findAll(PageRequest.of(pageNo, pageSize)))
                .filter((item)->(!item.isEmpty()))
                .map(page -> {
                    log.info("Find all the products from page {}", page.getNumber());
                    return page.map(product -> conversionService.convert(product, GetProductResponse.class)).stream().toList();
                })
                .orElseThrow(() -> {
                    log.error("No products found");
                    return new ApplicationException(ErrorType.NO_PRODUCTS_FOUND);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public GetProductResponse getProductById(UUID id) {
        GetProductResponse product = productRepository.findById(id)
                .map(item -> conversionService.convert(item, GetProductResponse.class))
                .orElseThrow(() -> {
                    log.error("Product with id {} not found", id);
                    return new ApplicationException(ErrorType.PRODUCT_NOT_FOUND);
                });
        log.info("Product found: {}", product);

        return product;
    }

    @Override
    @Transactional
    public GetProductResponse saveProduct(CreateProductDto product) {
        Product productEntity = productRepository.findBySku(product.getSku())
                .filter((item) -> {
                    log.error("Product with sku {} already exists", item.getSku());
                    throw new ApplicationException(ErrorType.UNIQUE_CONSTRAINT_EXCEPTION);
                }).orElseGet(() -> {
                    log.info("Product saved: {}", product);
                    return productRepository.save(conversionService.convert(product, Product.class));
                });

        return conversionService.convert(productEntity, GetProductResponse.class);
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
    public GetProductResponse updateProduct(UpdateProductDto productDto) {
        Product productEntity = productRepository.findBySku(productDto.getSku()).orElseThrow(() -> {
            log.error("Product with sku {} not found", productDto.getSku());
            return new ApplicationException(ErrorType.PRODUCT_NOT_FOUND);
        });

        Product product = Product.builder().
                id(productEntity.getId()).
                sku(productDto.getSku()).
                name(productDto.getName())
                .category(productDto.getCategory())
                .price(productDto.getPrice())
                .description(productDto.getDescription())
                .quantity(productDto.getQuantity())
                .updatedAt(!productDto.getQuantity().equals(productEntity.getQuantity()) ?
                        LocalDateTime.now() : productEntity.getUpdatedAt())
                .createdAt(productEntity.getCreatedAt())
                .build();

        log.info("Product updated: {}", productDto);
        return conversionService.convert(productRepository.save(product), GetProductResponse.class);
    }
}
