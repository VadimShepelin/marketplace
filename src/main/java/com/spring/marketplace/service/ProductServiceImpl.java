package com.spring.marketplace.service;

import com.spring.marketplace.dto.ProductDto;
import com.spring.marketplace.exception.ApplicationException;
import com.spring.marketplace.model.Product;
import com.spring.marketplace.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ConversionService conversionService;

    @Override
    public List<ProductDto> getProducts() {
        List<ProductDto> allProductsList = productRepository.findAll().stream()
                .map(item -> conversionService.convert(item, ProductDto.class))
                .collect(Collectors.toList());
        log.info("All products list: {}", allProductsList);

        return allProductsList;
    }

    @Override
    public ProductDto getProduct(UUID id) {
        ProductDto product = productRepository.findById(id)
                .map(item -> conversionService.convert(item, ProductDto.class))
                .orElseThrow(() -> {
                    log.error("Product with id {} not found", id);
                    return new ApplicationException(ApplicationException.ErrorType.PRODUCT_NOT_FOUND);
                });
        log.info("Product found: {}", product);

        return product;
    }

    @Override
    public ProductDto saveProduct(ProductDto product) {
        Product productEntity = conversionService.convert(product, Product.class);
        ProductDto productDto = conversionService.convert(productRepository.save(productEntity), ProductDto.class);
        log.info("Product saved with success: {}", productDto);

        return productDto;
    }

    @Override
    public void deleteProduct(UUID id) {
        productRepository.findById(id)
                .ifPresentOrElse(item -> productRepository.deleteById(item.getId()),
                        () -> {
                    log.error("Product with id {} was not deleted", id);
                    throw new ApplicationException(ApplicationException.ErrorType.PRODUCT_NOT_FOUND);});
        log.info("Product with id {} deleted", id);
    }

    @Override
    public ProductDto updateProduct(ProductDto product) {
        Product updatedProduct = productRepository.save(conversionService.convert(product, Product.class));
        log.info("Product updated with success: {}", updatedProduct);

        return conversionService.convert(updatedProduct,ProductDto.class);
    }
}
