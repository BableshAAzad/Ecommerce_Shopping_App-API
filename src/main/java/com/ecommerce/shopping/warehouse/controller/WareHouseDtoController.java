package com.ecommerce.shopping.warehouse.controller;

import com.ecommerce.shopping.config.WebClientProvider;
import com.ecommerce.shopping.image.service.ImageService;
import com.ecommerce.shopping.utility.ResponseStructure;
import com.ecommerce.shopping.warehouse.dto.*;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class WareHouseDtoController {

    private final WebClientProvider webClientProvider;
    private final ImageService imageService;

    //---------------------------------------------------------------------------------------------------
    // /storages/sellers/{sellerId}?page=0&size=10
    @GetMapping("/sellers/{sellerId}/storages")
    public ResponseStructure<PagedModel<Storage>> findStoragesBySellerId(
            @PathVariable Long sellerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return webClientProvider.getStoragesBySellerId(sellerId, page, size);
    }

    //---------------------------------------------------------------------------------------------------
//    /wareHouses/{wareHouseId}/storages?page=0&size=10
    @GetMapping("/wareHouses/{wareHouseId}/storages")
    public ResponseStructure<PagedModel<Storage>> findStoragesByWareHouseId(
            @PathVariable Long wareHouseId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return webClientProvider.getStoragesByWareHouseId(wareHouseId, page, size);
    }

    //---------------------------------------------------------------------------------------------------
//  /addresses/{city}/wareHouses?page=0&size=10
    @GetMapping("/addresses/{city}/wareHouses")
    public ResponseStructure<PagedModel<Map<String, Object>>> findWareHousesByCity(
            @PathVariable String city,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return webClientProvider.getWareHousesByCity(city, page, size);
    }

    //---------------------------------------------------------------------------------------------------
//    /wareHouses-with-address?page=0&size=10
    @GetMapping("/wareHouses-with-address")
    public ResponseStructure<PagedModel<Map<String, Object>>> findStoreHousesWithAddressForClient(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return webClientProvider.getStoreHousesWithAddressForClient(page, size);
    }
    //---------------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------

    //---------------------------------------------------------------------------------------------------

    //---------------------------------------------------------------------------------------------------

}
