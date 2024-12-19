package com.spring.marketplace.service;

import com.spring.marketplace.dto.ProductReadDto;
import com.spring.marketplace.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService<UUID, ProductReadDto> {

    private final ProductRepository productRepository;
    private final ConversionService conversionService;

    @Override
    public List<ProductReadDto> getProducts() {
       return productRepository.findAll().stream()
                .map(item -> conversionService.convert(item, ProductReadDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ProductReadDto> getProduct(UUID id) {
        return Optional.empty();
    }

    @Override
    public void saveProduct(ProductReadDto product) {

    }

    @Override
    public void deleteProduct(UUID id) {

    }

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ConversionService conversionService) {
        this.productRepository = productRepository;
        this.conversionService = conversionService;
    }
}
