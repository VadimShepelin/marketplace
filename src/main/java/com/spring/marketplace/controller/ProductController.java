package com.spring.marketplace.controller;

import com.spring.marketplace.dto.ProductDto;
import com.spring.marketplace.exception.ConstraintValidationException;
import com.spring.marketplace.exception.ProductAlreadyExistsException;
import com.spring.marketplace.exception.ProductDontHaveIdException;
import com.spring.marketplace.repository.ProductRepository;
import com.spring.marketplace.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public List<ProductDto> getAllProducts() {
        return productService.getProducts();
    }

    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable UUID id) {
        return (ProductDto) productService.getProduct(id);
    }

    @DeleteMapping("/{id}")
    public String deleteProductById(@PathVariable UUID id) {
        productService.deleteProduct(id);
        return "Product with id: " + id + " was deleted";
    }

    @PostMapping
    public ProductDto createProduct(@Valid @RequestBody  ProductDto productDto, BindingResult bindingResult) {
      if (bindingResult.hasErrors()) {
          String allErrors = bindingResult.getAllErrors().stream().
                  map((item) -> item.getDefaultMessage())
                  .collect(Collectors.joining(", "));
          throw new ConstraintValidationException(allErrors);
      }

      if(productDto.getId() != null) {
          throw new ProductAlreadyExistsException("Product id is already in use");
      }

       return (ProductDto) productService.saveProduct(productDto);
    }

    @PutMapping
    public ProductDto updateProduct(@Valid @RequestBody ProductDto productDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String allErrors = bindingResult.getAllErrors().stream().
                    map((item) -> item.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            throw new ConstraintValidationException(allErrors);
        }

        if(productDto.getId()==null){
            throw new ProductDontHaveIdException("Product dont have id");
        }

        return (ProductDto) productService.updateProduct(productDto);
    }

    @Autowired
    public ProductController(ProductService productService, ProductRepository productRepository) {
        this.productService = productService;
    }
}
