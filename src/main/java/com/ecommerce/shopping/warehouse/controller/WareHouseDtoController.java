package com.ecommerce.shopping.warehouse.controller;

import com.ecommerce.shopping.config.RestTemplateProvider;
import com.ecommerce.shopping.image.service.ImageService;
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
    private final ImageService imageService;

    //---------------------------------------------------------------------------------------------------
    @GetMapping("/products/{productId}")
    public ResponseEntity<ResponseStructure<Product>> findProduct(@PathVariable Long productId) {
        return restTemplateProvider.getProduct(productId);
    }

    //---------------------------------------------------------------------------------------------------
    @GetMapping("/products") // GET /products?page=0&size=10
    public ResponseEntity<ResponseStructure<PagedModel<Product>>> findProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
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
    // /storages/sellers/{sellerId}?page=0&size=10
    @GetMapping("/sellers/{sellerId}/storages")
    public ResponseEntity<ResponseStructure<PagedModel<Storage>>> findStoragesBySellerId(
            @PathVariable Long sellerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return restTemplateProvider.getStoragesBySellerId(sellerId, page, size);
    }

    //---------------------------------------------------------------------------------------------------
//    /wareHouses/{wareHouseId}/storages?page=0&size=10
    @GetMapping("/wareHouses/{wareHouseId}/storages")
    public ResponseEntity<ResponseStructure<PagedModel<Storage>>> findStoragesByWareHouseId(
            @PathVariable Long wareHouseId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return restTemplateProvider.getStoragesByWareHouseId(wareHouseId, page, size);
    }

    //---------------------------------------------------------------------------------------------------
//  /addresses/{city}/wareHouses?page=0&size=10
    @GetMapping("/addresses/{city}/wareHouses")
    public ResponseEntity<ResponseStructure<PagedModel<Map<String, Object>>>> findWareHousesByCity(
            @PathVariable String city,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return restTemplateProvider.getWareHousesByCity(city, page, size);
    }

    //---------------------------------------------------------------------------------------------------
//    /wareHouses-with-address?page=0&size=10
    @GetMapping("/wareHouses-with-address")
    public ResponseEntity<ResponseStructure<PagedModel<Map<String, Object>>>> findStoreHousesWithAddressForClient(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return restTemplateProvider.getStoreHousesWithAddressForClient(page, size);
    }
    //---------------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------

    //---------------------------------------------------------------------------------------------------

    //---------------------------------------------------------------------------------------------------

}
