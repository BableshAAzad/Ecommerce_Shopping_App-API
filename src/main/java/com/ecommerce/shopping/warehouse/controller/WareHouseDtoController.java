package com.ecommerce.shopping.warehouse.controller;

import com.ecommerce.shopping.config.RestTemplateProvider;
import com.ecommerce.shopping.utility.ResponseStructure;
import com.ecommerce.shopping.warehouse.dto.*;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class WareHouseDtoController {
    private final RestTemplateProvider restTemplateProvider;

    //---------------------------------------------------------------------------------------------------
    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> findProduct(@PathVariable Long productId) {
        Product product = restTemplateProvider.getProduct(productId);
        return ResponseEntity.ok(product);
    }

    //---------------------------------------------------------------------------------------------------
    @GetMapping("/products") // GET /products?page=0&size=10
    public ResponseEntity<ResponseStructure<PagedModel<Product>>> findProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return restTemplateProvider.getProducts(page, size);
    }

    //---------------------------------------------------------------------------------------------------
    @GetMapping("/products/search/{query}") //products/search/query?page=0&size=10
    public ResponseEntity<ResponseStructure<PagedModel<Product>>> searchProducts(
            @PathVariable String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return restTemplateProvider.searchProducts(query, page, size);
    }

    //---------------------------------------------------------------------------------------------------
    @PostMapping("/products/filter") //products/filter?page=0&size=10
    public ResponseEntity<ResponseStructure<PagedModel<Product>>> filterProducts(
            @RequestBody InventorySearchCriteria inventorySearchCriteria,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return restTemplateProvider.filterProducts(inventorySearchCriteria, page, size);
    }

    //---------------------------------------------------------------------------------------------------
    @GetMapping("/sellers/{sellerId}/products") //sellers/sellerId/products?page=0&size=10
    public ResponseEntity<ResponseStructure<PagedModel<Product>>> findProductsBySellerId(
            @PathVariable Long sellerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return restTemplateProvider.getProductsBySellerId(sellerId, page, size);
    }

    //---------------------------------------------------------------------------------------------------
    @PostMapping("/storages/{storageId}/products")
    public ResponseEntity<Product> addProduct(@PathVariable Long storageId,
                                              @RequestParam int quantity,
                                              @RequestBody ProductRequest productRequest) {
        Product product = restTemplateProvider.addProduct(storageId, quantity, productRequest);
        return ResponseEntity.ok(product);
    }

    //---------------------------------------------------------------------------------------------------
    @PutMapping("/sellers/products/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long productId,
                                                 @RequestBody ProductRequest productRequest) {
        Product product = restTemplateProvider.updateProduct(productId, productRequest);
        return ResponseEntity.ok(product);
    }

    //---------------------------------------------------------------------------------------------------
    @GetMapping("/storages/sellers/{sellerId}")
    public ResponseEntity<List<Storage>> findStoragesBySellerId(@PathVariable Long sellerId) {
        List<Storage> storages = restTemplateProvider.getStoragesBySellerId(sellerId);
        return ResponseEntity.ok(storages);
    }

    //---------------------------------------------------------------------------------------------------
    @GetMapping("/wareHouses/{wareHouseId}/storages")
    public ResponseEntity<List<Storage>> findStoragesByWareHouseId(@PathVariable Long wareHouseId) {
        List<Storage> storages = restTemplateProvider.getStoragesByWareHouseId(wareHouseId);
        return ResponseEntity.ok(storages);
    }

    //---------------------------------------------------------------------------------------------------
    @GetMapping("/addresses/{city}/wareHouses")
    public ResponseEntity<List<Map<String, Object>>> findWareHousesByCity(@PathVariable String city) {
        List<Map<String, Object>> wareHouses = restTemplateProvider.getWareHousesByCity(city);
        return ResponseEntity.ok(wareHouses);
    }

    //---------------------------------------------------------------------------------------------------
    @GetMapping("/wareHouses-with-address")
    public ResponseEntity<List<Map<String, Object>>> findStoreHousesWithAddressForClient() {
        List<Map<String, Object>> wareHouses = restTemplateProvider.getStoreHousesWithAddressForClient();
        return ResponseEntity.ok(wareHouses);
    }
    //---------------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------

    //---------------------------------------------------------------------------------------------------

    //---------------------------------------------------------------------------------------------------

}
