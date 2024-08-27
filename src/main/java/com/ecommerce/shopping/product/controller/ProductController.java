package com.ecommerce.shopping.product.controller;

import com.ecommerce.shopping.config.WebClientProvider;
import com.ecommerce.shopping.product.dto.ProductResponse;
import com.ecommerce.shopping.product.service.ProductService;
import com.ecommerce.shopping.utility.ResponseStructure;
import com.ecommerce.shopping.product.dto.ProductRequest;
import com.ecommerce.shopping.warehouse.dto.Inventory;
import com.ecommerce.shopping.warehouse.dto.InventorySearchCriteria;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final WebClientProvider webClientProvider;

    //---------------------------------------------------------------------------------------------------
    // /sellers/products/{productId}/stocks?quantity=5
    @PutMapping("/sellers/products/{productId}/stocks")
    public Mono<ResponseStructure<ProductResponse>> updateProduct(
            @PathVariable Long productId,
            @RequestParam int quantity,
            @RequestParam(value = "productImage", required = false) MultipartFile productImage,
            @Valid @ModelAttribute ProductRequest productRequest) {
        return productService.updateProduct(productId, quantity, productImage, productRequest);
    }

    //---------------------------------------------------------------------------------------------------
    // /storages/{storageId}/products?quantity=5
    @PostMapping("/storages/{storageId}/products")
    public ResponseStructure<ProductResponse> addProduct(
            @PathVariable Long storageId,
            @RequestParam int quantity,
            @RequestParam("productImage") MultipartFile productImage,
            @Valid @ModelAttribute ProductRequest productRequest) {
        return productService.addProduct(storageId, quantity, productImage, productRequest);
    }

    //---------------------------------------------------------------------------------------------------
    @GetMapping("/products/{productId}")
    public ResponseStructure<Inventory> findProduct(@PathVariable Long productId) {
        return webClientProvider.getProduct(productId);
    }

    //---------------------------------------------------------------------------------------------------
    @GetMapping("/products") // GET /products?page=0&size=10
    public ResponseStructure<PagedModel<Inventory>> findProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return webClientProvider.getProducts(page, size);
    }

    //---------------------------------------------------------------------------------------------------
    @GetMapping("/products/search/{query}") // products/search/query?page=0&size=10
    public ResponseStructure<PagedModel<Inventory>> searchProducts(
            @PathVariable String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return webClientProvider.searchProducts(query, page, size);
    }

    //---------------------------------------------------------------------------------------------------
    @PostMapping("/products/filter") // products/filter?page=0&size=10
    public ResponseStructure<PagedModel<Inventory>> filterProducts(
            @RequestBody InventorySearchCriteria inventorySearchCriteria,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return webClientProvider.filterProducts(inventorySearchCriteria, page, size);
    }

    //---------------------------------------------------------------------------------------------------
    @GetMapping("/sellers/{sellerId}/products") // sellers/sellerId/products?page=0&size=10
    public ResponseStructure<PagedModel<Inventory>> findProductsBySellerId(
            @PathVariable Long sellerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return webClientProvider.getProductsBySellerId(sellerId, page, size);
    }

    //---------------------------------------------------------------------------------------------------

}
