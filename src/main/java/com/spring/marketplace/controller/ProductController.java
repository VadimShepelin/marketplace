package com.spring.marketplace.controller;

import com.spring.marketplace.dto.ProductDto;
import com.spring.marketplace.exception.ConstraintValidationException;
import com.spring.marketplace.exception.ProductAlreadyExistsException;
import com.spring.marketplace.exception.ProductDontHaveIdException;
import com.spring.marketplace.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
          String allErrorsMessages = bindingResult.getAllErrors().stream().
                  map((item) -> item.getDefaultMessage())
                  .collect(Collectors.joining(", "));
          throw new ConstraintValidationException(allErrorsMessages);
      }

      if(productDto.getId() != null) {
          throw new ProductAlreadyExistsException("Product id is already in use");
      }

       return (ProductDto) productService.saveProduct(productDto);
    }

    @PutMapping
    public ProductDto updateProduct(@Valid @RequestBody ProductDto productDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String allErrorsMessages = bindingResult.getAllErrors().stream().
                    map((item) -> item.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            throw new ConstraintValidationException(allErrorsMessages);
        }

        if(productDto.getId()==null){
            throw new ProductDontHaveIdException("Product doesn't have id");
        }

        return (ProductDto) productService.updateProduct(productDto);
    }

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
}
