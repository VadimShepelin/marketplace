package com.spring.marketplace.service.impl;

import com.spring.marketplace.aspects.LogExecutionTime;
import com.spring.marketplace.dto.CreateProductDto;
import com.spring.marketplace.dto.GetProductResponse;
import com.spring.marketplace.dto.ProductFilterDto;
import com.spring.marketplace.dto.UpdateProductDto;
import com.spring.marketplace.exception.ApplicationException;
import com.spring.marketplace.model.Product;
import com.spring.marketplace.repository.ProductRepository;
import com.spring.marketplace.service.ExchangeService;
import com.spring.marketplace.service.ProductService;
import com.spring.marketplace.service.ReportService;
import com.spring.marketplace.specification.ProductSpecification;
import com.spring.marketplace.utils.enums.ErrorType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
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
    private final ReportService reportService;
    private final ExchangeService<BigDecimal,BigDecimal> exchangeService;

    @Override
    @LogExecutionTime
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
    @LogExecutionTime
    public GetProductResponse getProductById(UUID id) {
        GetProductResponse product = productRepository.findById(id)
                .map(item -> conversionService.convert(item, GetProductResponse.class))
                .orElseThrow(() -> {
                    log.error("Product with id {} not found", id);
                    return new ApplicationException(ErrorType.PRODUCT_NOT_FOUND);
                });
        log.info("Product found: {}", product);

        product.setPrice(exchangeService.convertCurrency(product.getPrice()));
        return product;
    }

    @Override
    @Transactional
    @LogExecutionTime
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
    @LogExecutionTime
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
    @LogExecutionTime
    public GetProductResponse updateProduct(UpdateProductDto productDto) {
        Product productEntity = productRepository.findBySku(productDto.getSku()).orElseThrow(() -> {
            log.error("Product with sku {} not found", productDto.getSku());
            return new ApplicationException(ErrorType.PRODUCT_NOT_FOUND);
        });

        productEntity.setSku(productDto.getSku());
        productEntity.setName(productDto.getName());
        productEntity.setCategory(productDto.getCategory());
        productEntity.setPrice(productDto.getPrice());
        productEntity.setDescription(productDto.getDescription());
        productEntity.setAvailable(productDto.getIsAvailable()==null? true : productDto.getIsAvailable());
        productEntity.setQuantity(productDto.getQuantity());
        productEntity.setUpdatedAt(!productDto.getQuantity().equals(productEntity.getQuantity()) ?
                LocalDateTime.now() : productEntity.getUpdatedAt());
        productEntity.setCreatedAt(productEntity.getUpdatedAt());


        log.info("Product updated: {}", productDto);
        return conversionService.convert(productRepository.save(productEntity), GetProductResponse.class);
    }

    @Override
    @Transactional
    @LogExecutionTime
    public List<GetProductResponse> searchProductsWithFilter(ProductFilterDto productFilter) {
        List<Product> productsList = Optional.of(productRepository.findAll(
                Specification.where(ProductSpecification.byName(productFilter.getName()))
                        .and(ProductSpecification.byQuantity(productFilter.getQuantity()))
                        .and(ProductSpecification.byPrice(productFilter.getPrice()))
                        .and(ProductSpecification.byAvailability(productFilter.getIsAvailable()))
        )).orElseThrow(() -> {
            log.error("No products found");
            return new ApplicationException(ErrorType.NO_PRODUCTS_FOUND);
        });

        reportService.exportAllProductsInXlsxFile(productsList);
        log.info("Found {} products", productsList);
        return productsList.stream().map((product) -> conversionService.convert(product, GetProductResponse.class)).toList();
    }
}
