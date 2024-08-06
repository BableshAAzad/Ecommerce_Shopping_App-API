package com.ecommerce.shopping.address.controller;

import com.ecommerce.shopping.address.dto.AddressRequest;
import com.ecommerce.shopping.address.dto.AddressResponse;
import com.ecommerce.shopping.address.service.AddressService;
import com.ecommerce.shopping.utility.ResponseStructure;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping("/users/{userId}/addresses")
    public ResponseEntity<ResponseStructure<AddressResponse>> addAddress(
            @RequestBody AddressRequest addressRequest,
            @PathVariable Long userId) {
        return addressService.addAddress(addressRequest, userId);
    }

    @GetMapping("/users/{userId}/addresses")
    public ResponseEntity<ResponseStructure<List<AddressResponse>>> getAddress(@PathVariable Long userId) {
        return addressService.getAddress(userId);
    }

    @PutMapping("/users/addresses/{addressId}")
    public ResponseEntity<ResponseStructure<AddressResponse>> updateAddress(
            @PathVariable Long addressId,
            @RequestBody AddressRequest addressRequest) {
        return addressService.updateAddress(addressId, addressRequest);
    }


}
