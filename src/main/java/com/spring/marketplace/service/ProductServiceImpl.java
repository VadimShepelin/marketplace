package com.spring.marketplace.service;

import com.spring.marketplace.dto.ProductDto;
import com.spring.marketplace.exception.NoSuchProductException;
import com.spring.marketplace.model.Product;
import com.spring.marketplace.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService<UUID, ProductDto> {

    private final ProductRepository productRepository;
    private final ConversionService conversionService;

    @Override
    public List<ProductDto> getProducts() {
       return productRepository.findAll().stream()
                .map(item -> conversionService.convert(item, ProductDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto getProduct(UUID id) {
         return productRepository.findById(id)
                 .map(item->conversionService.convert(item,ProductDto.class))
                 .orElseThrow(() -> new NoSuchProductException("Can't find product with id: " + id));
    }

    @Override
    public ProductDto saveProduct(ProductDto product) {
        Product productEntity = conversionService.convert(product, Product.class);
        return conversionService.convert(productRepository.save(productEntity),ProductDto.class);
    }



    @Override
    public void deleteProduct(UUID id) {
        productRepository.findById(id)
                .ifPresentOrElse(item -> productRepository.deleteById(item.getId()),
                        () -> {throw new NoSuchProductException("Can't find product with id: " + id);});
    }

    @Override
    public ProductDto updateProduct(ProductDto product) {
        Product updatedProduct = productRepository.save(conversionService.convert(product, Product.class));
        return conversionService.convert(updatedProduct,ProductDto.class);
    }

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ConversionService conversionService) {
        this.productRepository = productRepository;
        this.conversionService = conversionService;
    }
}
