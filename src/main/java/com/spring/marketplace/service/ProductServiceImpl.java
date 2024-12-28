package com.spring.marketplace.service;

import com.spring.marketplace.dto.CreateProductDto;
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
    public List<CreateProductDto> getAllProducts(int pageNo, int pageSize) {
        return Optional.of(productRepository.findAll(PageRequest.of(pageNo, pageSize)))
                .filter((item)->(!item.isEmpty()))
                .map(page -> {
                    log.info("Find all the products from page {}", page.getNumber());
                    return page.map(product -> conversionService.convert(product, CreateProductDto.class)).stream().toList();
                })
                .orElseThrow(() -> {
                    log.error("No products found");
                    return new ApplicationException(ErrorType.NO_PRODUCTS_FOUND);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public CreateProductDto getProductById(UUID id) {
        CreateProductDto product = productRepository.findById(id)
                .map(item -> conversionService.convert(item, CreateProductDto.class))
                .orElseThrow(() -> {
                    log.error("Product with id {} not found", id);
                    return new ApplicationException(ErrorType.PRODUCT_NOT_FOUND);
                });
        log.info("Product found: {}", product);

        return product;
    }

    @Override
    @Transactional
    public CreateProductDto saveProduct(CreateProductDto product) {
        productRepository.findBySku(product.getSku())
                        .ifPresentOrElse((item) ->{
                            log.error("Product with sku {} already exists", item.getSku());
                            throw new ApplicationException(ErrorType.UNIQUE_CONSTRAINT_EXCEPTION);
                        },() ->{
                            productRepository.save(conversionService.convert(product, Product.class));
                            log.info("Product saved: {}", product);
                        });

        return product;
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
    public CreateProductDto updateProduct(CreateProductDto product) {
       productRepository.findById(product.getId())
                .ifPresentOrElse(item -> {
                    productRepository.findBySku(product.getSku())
                                    .ifPresentOrElse((element)->{
                                        log.error("Product with this sku {} already exists", element.getSku());
                                        throw new ApplicationException(ErrorType.UNIQUE_CONSTRAINT_EXCEPTION);
                                    },() ->{
                                        productRepository.save(conversionService.convert(product, Product.class));
                                        log.info("Product updated: {}", product);
                                    });
                    }, () -> {log.error("Product dont exists");
                    throw new ApplicationException(ErrorType.PRODUCT_DONT_EXISTS);
                });

       return conversionService.convert(product, CreateProductDto.class);
    }
}
