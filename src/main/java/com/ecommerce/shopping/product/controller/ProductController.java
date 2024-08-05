package com.ecommerce.shopping.product.controller;

import com.ecommerce.shopping.product.dto.ProductResponseDto;
import com.ecommerce.shopping.product.service.ProductService;
import com.ecommerce.shopping.utility.ResponseStructure;
import com.ecommerce.shopping.warehouse.dto.ProductRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    //---------------------------------------------------------------------------------------------------
    @PutMapping("/sellers/products/{productId}")
    public ResponseEntity<ResponseStructure<ProductResponseDto>> updateProduct(
            @PathVariable Long productId,
            @RequestParam(value = "productImage", required = false) MultipartFile productImage,
            @ModelAttribute ProductRequest productRequest) {
        return productService.updateProduct(productId, productImage, productRequest);
    }

    //---------------------------------------------------------------------------------------------------
    @PostMapping("/storages/{storageId}/products")
    public ResponseEntity<ResponseStructure<ProductResponseDto>> addProduct(@PathVariable Long storageId,
                                                                            @RequestParam int quantity,
                                                                            @RequestParam("productImage") MultipartFile productImage,
                                                                            @ModelAttribute ProductRequest productRequest) {
        return productService.addProduct(storageId, quantity, productImage, productRequest);
    }

    //---------------------------------------------------------------------------------------------------


}
