package com.ecommerce.shopping.product.controller;

import com.ecommerce.shopping.config.RestTemplateProvider;
import com.ecommerce.shopping.product.dto.Product;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class ProductDtoController {
    private final RestTemplateProvider restTemplateProvider;

    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> findProduct(@PathVariable Long productId) {
        Product product = restTemplateProvider.getProduct(productId);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> findProducts() {
        List<Product> products = restTemplateProvider.getProducts();
        return ResponseEntity.ok(products);
    }
}
