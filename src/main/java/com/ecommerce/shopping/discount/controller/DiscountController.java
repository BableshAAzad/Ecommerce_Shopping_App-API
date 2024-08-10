package com.ecommerce.shopping.discount.controller;

import com.ecommerce.shopping.discount.dto.DiscountRequest;
import com.ecommerce.shopping.discount.dto.DiscountResponse;
import com.ecommerce.shopping.discount.service.DiscountService;
import com.ecommerce.shopping.utility.ResponseStructure;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @PutMapping("/products/discounts/{discountId}")
    public ResponseEntity<ResponseStructure<DiscountResponse>> updateDiscount(
            @PathVariable Long discountId,
            @Valid @RequestBody DiscountRequest discountRequest) {
        return discountService.updateDiscount(discountId, discountRequest);
    }
    //---------------------------------------------------------------------------------------------------

    //---------------------------------------------------------------------------------------------------

    //---------------------------------------------------------------------------------------------------

}
