package com.spring.marketplace.service;

import com.spring.marketplace.dto.ProductDto;
import com.spring.marketplace.exception.ApplicationException;
import com.spring.marketplace.model.Product;
import com.spring.marketplace.repository.ProductRepository;
import com.spring.marketplace.utils.enums.ErrorType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ConversionService conversionService;

    @Override
    @Transactional(readOnly = true)
    public List<ProductDto> getAllProducts() {
        List<Product> allProductsList = productRepository.findAll();
        if (allProductsList.isEmpty()) {
            log.info("No products found");
            throw new ApplicationException(ErrorType.NO_PRODUCTS_FOUND);
        }

        log.info("All products list: {}", allProductsList);
        return allProductsList.stream().
                map((item) -> conversionService.convert(item, ProductDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDto getProductById(UUID id) {
        ProductDto product = productRepository.findById(id)
                .map(item -> conversionService.convert(item, ProductDto.class))
                .orElseThrow(() -> {
                    log.error("Product with id {} not found", id);
                    return new ApplicationException(ErrorType.PRODUCT_NOT_FOUND);
                });
        log.info("Product found: {}", product);

        return product;
    }

    @Override
    @Transactional
    public ProductDto saveProduct(ProductDto product) {
        Product productEntity = conversionService.convert(product, Product.class);
        ProductDto productDto = conversionService.convert(productRepository.save(productEntity), ProductDto.class);
        log.info("Product saved with success: {}", productDto);

        return productDto;
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
    public ProductDto updateProduct(ProductDto product) {
       productRepository.findById(product.getId())
                .ifPresentOrElse(item -> {
                            log.info("Product with id {} updated", product.getId());
                            productRepository.save(conversionService.convert(product, Product.class));
                        },
                        () -> {
                            log.error("Product dont exists");
                            throw new ApplicationException(ErrorType.PRODUCT_DONT_EXISTS);
                        });

       return conversionService.convert(product,ProductDto.class);
    }
}
