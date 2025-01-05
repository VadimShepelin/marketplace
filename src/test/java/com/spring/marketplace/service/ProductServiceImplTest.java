package com.spring.marketplace.service;

import com.spring.marketplace.dto.CreateProductDto;
import com.spring.marketplace.dto.GetProductResponse;
import com.spring.marketplace.dto.UpdateProductDto;
import com.spring.marketplace.exception.ApplicationException;
import com.spring.marketplace.model.Product;
import com.spring.marketplace.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private ConversionService conversionService;
    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;
    private UUID productId;
    private GetProductResponse getProductResponse;

    @BeforeEach
    void setUp() {
        productId = UUID.randomUUID();

        product = Product.builder()
                .id(productId)
                .sku("TEST SKU")
                .build();

        getProductResponse = GetProductResponse.builder()
                .sku("TEST SKU")
                .build();
    }

    @Test
    @DisplayName("Find product by id successfully")
    void getProductById_shouldReturnProduct() {
        doReturn(Optional.of(product)).when(productRepository).findById(productId);
        doReturn(getProductResponse).when(conversionService).convert(product, GetProductResponse.class);

        GetProductResponse actualResult = productService.getProductById(productId);

        assertNotNull(actualResult);
        assertEquals(getProductResponse, actualResult);
    }

    @Test
    @DisplayName("Get all products list successfully")
    void getAllProducts_shouldReturnProductsList() {
        Page page = new PageImpl<>(List.of(product));
        doReturn(page).when(productRepository).findAll(any(PageRequest.class));
        doReturn(getProductResponse).when(conversionService).convert(product, GetProductResponse.class);

        List<GetProductResponse> actualResult = productService.getAllProducts(0, 12);

        assertNotNull(actualResult);
        assertEquals(actualResult, List.of(getProductResponse));
    }

    @Test
    @DisplayName("Successfully delete the product")
    void deleteProduct_shouldDeleteProduct() {
        doReturn(Optional.of(product)).when(productRepository).findById(productId);
        doNothing().when(productRepository).deleteById(productId);

        productService.deleteProduct(productId);

        verify(productRepository, times(1)).deleteById(productId);
    }

    @Test
    @DisplayName("Successfully save the product")
    void saveProduct_shouldSaveProduct() {
        CreateProductDto productDto = CreateProductDto.builder()
                .sku("TEST SKU")
                .build();

        doReturn(Optional.empty()).when(productRepository).findBySku(productDto.getSku());
        doReturn(product).when(conversionService).convert(productDto,Product.class);
        doReturn(product).when(productRepository).save(product);
        doReturn(getProductResponse).when(conversionService).convert(product,GetProductResponse.class);

        GetProductResponse actualResult = productService.saveProduct(productDto);

        assertNotNull(actualResult);
        assertEquals(getProductResponse, actualResult);
    }

    @Test
    @DisplayName("Successfully updated the product")
    void updateProduct_shouldUpdateProduct() {
        UpdateProductDto productDto = UpdateProductDto.builder()
                .sku("TEST SKU")
                .name("TEST NAME")
                .description("TEST DESCRIPTION")
                .quantity(BigInteger.ONE)
                .price(BigDecimal.ONE)
                .build();

        doReturn(Optional.of(product)).when(productRepository).findBySku(productDto.getSku());
        doReturn(product).when(productRepository).save(product);
        doReturn(getProductResponse).when(conversionService).convert(product,GetProductResponse.class);

        GetProductResponse actualResult = productService.updateProduct(productDto);

        assertNotNull(actualResult);
        assertEquals(productDto.getSku(),product.getSku());
    }

    @Test
    @DisplayName("Product by this id not found")
    void getProductById_shouldThrowException_whenProductNotFound() {
        doReturn(Optional.empty()).when(productRepository).findById(productId);

        ApplicationException applicationException = assertThrows(ApplicationException.class, () -> productService.getProductById(productId));
        assertEquals("No such product", applicationException.getMessage());
    }

    @Test
    @DisplayName("Returns the empty product page")
    void getAllProducts_shouldThrowException_whenProductListIsEmpty(){
        doReturn(Page.empty()).when(productRepository).findAll(PageRequest.of(0,10));

        ApplicationException applicationException = assertThrows(ApplicationException.class, () -> productService.getAllProducts(0,10));
        assertEquals("No products found", applicationException.getMessage());
    }

    @Test
    @DisplayName("Save product with sku that already exists")
    void saveProduct_shouldThrowException_whenProductWithThisSkuAlreadyExists(){
        CreateProductDto productDto = CreateProductDto.builder()
                .sku("EXIST SKU")
                .build();

        doReturn(Optional.of(product)).when(productRepository).findBySku(productDto.getSku());

        ApplicationException applicationException = assertThrows(ApplicationException.class, () -> productService.saveProduct(productDto));
        assertEquals("A product with the same sku already exists", applicationException.getMessage());
    }

}