package com.ecommerce.shopping.warehouse.controller;

import com.ecommerce.shopping.config.RestTemplateProvider;
import com.ecommerce.shopping.warehouse.dto.*;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping("/wareHouses/{wareHouseId}")
    public ResponseEntity<WareHouse> findWareHouse(@PathVariable Long wareHouseId) {
        WareHouse wareHouse = restTemplateProvider.getWareHouse(wareHouseId);
        return ResponseEntity.ok(wareHouse);
    }

    //---------------------------------------------------------------------------------------------------
    @GetMapping("/wareHouses")
    public ResponseEntity<List<WareHouse>> findWareHouses() {
        List<WareHouse> wareHouses = restTemplateProvider.getWareHouses();
        return ResponseEntity.ok(wareHouses);
    }

    //---------------------------------------------------------------------------------------------------
    @GetMapping("/storageTypes/{storageTypeId}")
    public ResponseEntity<StorageType> findStorageType(@PathVariable Long storageTypeId) {
        StorageType storageType = restTemplateProvider.getStorageType(storageTypeId);
        return ResponseEntity.ok(storageType);
    }

    //---------------------------------------------------------------------------------------------------
    @GetMapping("/storageTypes")
    public ResponseEntity<List<StorageType>> findStorageTypes() {
        List<StorageType> storageTypes = restTemplateProvider.getStorageTypes();
        return ResponseEntity.ok(storageTypes);
    }

    //---------------------------------------------------------------------------------------------------
    @GetMapping("/storages/{storageId}")
    public ResponseEntity<Storage> findStorage(@PathVariable Long storageId) {
        Storage storage = restTemplateProvider.getStorage(storageId);
        return ResponseEntity.ok(storage);
    }

    //---------------------------------------------------------------------------------------------------
    @GetMapping("/storages")
    public ResponseEntity<List<Storage>> findStorages() {
        List<Storage> storages = restTemplateProvider.getStorages();
        return ResponseEntity.ok(storages);
    }

    //---------------------------------------------------------------------------------------------------
    @GetMapping("/addresses/{addressId}")
    public ResponseEntity<Address> findAddress(@PathVariable Long addressId) {
        Address address = restTemplateProvider.getAddress(addressId);
        return ResponseEntity.ok(address);
    }

    //---------------------------------------------------------------------------------------------------
    @GetMapping("/addresses")
    public ResponseEntity<List<Address>> findAddresses() {
        List<Address> addresses = restTemplateProvider.getAddresses();
        return ResponseEntity.ok(addresses);
    }

    //---------------------------------------------------------------------------------------------------
    @GetMapping("/addresses/{city}/wareHouses")
    public ResponseEntity<List<Map<String, Object>>> findAddresses(@PathVariable String city) {
        List<Map<String, Object>> wareHouses = restTemplateProvider.getWareHousesByCity(city);
        return ResponseEntity.ok(wareHouses);
    }
    //---------------------------------------------------------------------------------------------------

    //---------------------------------------------------------------------------------------------------

    //---------------------------------------------------------------------------------------------------

    //---------------------------------------------------------------------------------------------------

}
