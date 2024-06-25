package com.ecommerce.shopping.seller.controller;

import com.ecommerce.shopping.seller.service.SellerService;
import com.ecommerce.shopping.user.dto.UserResponse;
import com.ecommerce.shopping.utility.ResponseStructure;
import jakarta.validation.Valid;
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
public class SellerController {

    private SellerService sellerService;

    //------------------------------------------------------------------------------------------------------------------------
    @GetMapping("/sellers/{sellerId}")
    public ResponseEntity<ResponseStructure<UserResponse>> findSeller(@Valid @PathVariable Long sellerId) {
        return sellerService.findSeller(sellerId);
    }

    //------------------------------------------------------------------------------------------------------------------------
    @GetMapping("/sellers")
    public ResponseEntity<ResponseStructure<List<UserResponse>>> findSellers() {
        return sellerService.findSellers();
    }
}
