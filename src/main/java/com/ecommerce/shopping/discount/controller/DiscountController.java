package com.ecommerce.shopping.discount.controller;

import com.ecommerce.shopping.discount.dto.DiscountRequest;
import com.ecommerce.shopping.discount.dto.DiscountResponse;
import com.ecommerce.shopping.discount.service.DiscountService;
import com.ecommerce.shopping.utility.ResponseStructure;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class DiscountController {

    private DiscountService discountService;

    //---------------------------------------------------------------------------------------------------
    @GetMapping("/products/{productId}/discounts")
    public ResponseEntity<ResponseStructure<List<DiscountResponse>>> getDiscounts(
            @PathVariable Long productId) {
        return discountService.getDiscounts(productId);
    }
    //---------------------------------------------------------------------------------------------------

    @PreAuthorize("hasAuthority('SELLER')")
    @PutMapping("/sellers/{sellerId}/discounts/{discountId}")
    public ResponseEntity<ResponseStructure<DiscountResponse>> updateDiscount(
            @PathVariable Long sellerId,
            @PathVariable Long discountId,
            @Valid @RequestBody DiscountRequest discountRequest) {
        return discountService.updateDiscount(sellerId, discountId, discountRequest);
    }
    //---------------------------------------------------------------------------------------------------

    //---------------------------------------------------------------------------------------------------

    //---------------------------------------------------------------------------------------------------

}
