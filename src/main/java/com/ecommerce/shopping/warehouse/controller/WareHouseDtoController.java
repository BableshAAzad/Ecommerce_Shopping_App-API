package com.ecommerce.shopping.warehouse.controller;

import com.ecommerce.shopping.config.RestTemplateProvider;
import com.ecommerce.shopping.warehouse.dto.*;
import lombok.AllArgsConstructor;
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
    @GetMapping("/products")
    public ResponseEntity<List<Product>> findProducts() {
        List<Product> products = restTemplateProvider.getProducts();
        return ResponseEntity.ok(products);
    }

    //---------------------------------------------------------------------------------------------------
    @GetMapping("/sellers/{sellerId}/products")
    public ResponseEntity<List<Product>> findProductsBySellerId(@PathVariable Long sellerId) {
        List<Product> products = restTemplateProvider.getProductsBySellerId(sellerId);
        return ResponseEntity.ok(products);
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
    @PutMapping("/products/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long productId,
                                              @RequestBody ProductRequest productRequest) {
        Product product = restTemplateProvider.updateProduct(productId, productRequest);
        return ResponseEntity.ok(product);
    }

    //---------------------------------------------------------------------------------------------------
//    @GetMapping("/wareHouses/{wareHouseId}")
//    public ResponseEntity<WareHouse> findWareHouse(@PathVariable Long wareHouseId) {
//        WareHouse wareHouse = restTemplateProvider.getWareHouse(wareHouseId);
//        return ResponseEntity.ok(wareHouse);
//    }

    //---------------------------------------------------------------------------------------------------
//    @GetMapping("/wareHouses")
//    public ResponseEntity<List<WareHouse>> findWareHouses() {
//        List<WareHouse> wareHouses = restTemplateProvider.getWareHouses();
//        return ResponseEntity.ok(wareHouses);
//    }

    //---------------------------------------------------------------------------------------------------
//    @GetMapping("/storageTypes/{storageTypeId}")
//    public ResponseEntity<StorageType> findStorageType(@PathVariable Long storageTypeId) {
//        StorageType storageType = restTemplateProvider.getStorageType(storageTypeId);
//        return ResponseEntity.ok(storageType);
//    }

    //---------------------------------------------------------------------------------------------------
//    @GetMapping("/storageTypes")
//    public ResponseEntity<List<StorageType>> findStorageTypes() {
//        List<StorageType> storageTypes = restTemplateProvider.getStorageTypes();
//        return ResponseEntity.ok(storageTypes);
//    }

    //---------------------------------------------------------------------------------------------------
    @GetMapping("/storages/{storageId}")
    public ResponseEntity<Storage> findStorage(@PathVariable Long storageId) {
        Storage storage = restTemplateProvider.getStorage(storageId);
        return ResponseEntity.ok(storage);
    }

    //---------------------------------------------------------------------------------------------------
//    @GetMapping("/storages")
//    public ResponseEntity<List<Storage>> findStorages() {
//        List<Storage> storages = restTemplateProvider.getStorages();
//        return ResponseEntity.ok(storages);
//    }

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
//    @PostMapping("/wareHouses/{wareHouseId}/storageTypes/{storageTypeId}/storages")
//    public ResponseEntity<Storage> addStorage(@PathVariable Long wareHouseId,
//                                              @PathVariable Long storageTypeId,
//                                              @RequestParam int no_of_storage_units,
//                                              @RequestBody StorageRequest storageRequest) {
//        Storage storage = restTemplateProvider.addStorage(wareHouseId, storageTypeId, no_of_storage_units, storageRequest);
//        return ResponseEntity.ok(storage);
//    }

    //---------------------------------------------------------------------------------------------------
//    @GetMapping("/addresses/{addressId}")
//    public ResponseEntity<Address> findAddress(@PathVariable Long addressId) {
//        Address address = restTemplateProvider.getAddress(addressId);
//        return ResponseEntity.ok(address);
//    }

    //---------------------------------------------------------------------------------------------------
//    @GetMapping("/addresses")
//    public ResponseEntity<List<Address>> findAddresses() {
//        List<Address> addresses = restTemplateProvider.getAddresses();
//        return ResponseEntity.ok(addresses);
//    }

    //---------------------------------------------------------------------------------------------------
    @GetMapping("/addresses/{city}/wareHouses")
    public ResponseEntity<List<Map<String, Object>>> findAddresses(@PathVariable String city) {
        List<Map<String, Object>> wareHouses = restTemplateProvider.getWareHousesByCity(city);
        return ResponseEntity.ok(wareHouses);
    }
    //---------------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------
    @GetMapping("/wareHouses-with-address")
    public ResponseEntity<List<Map<String, Object>>> findStoreHousesWithAddressForClient() {
        List<Map<String, Object>> wareHouses = restTemplateProvider.getStoreHousesWithAddressForClient();
        return ResponseEntity.ok(wareHouses);
    }
    //---------------------------------------------------------------------------------------------------

    //---------------------------------------------------------------------------------------------------

    //---------------------------------------------------------------------------------------------------

}
