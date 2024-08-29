package com.ecommerce.shopping.discount.service;

import com.ecommerce.shopping.discount.dto.DiscountRequest;
import com.ecommerce.shopping.discount.dto.DiscountResponse;
import com.ecommerce.shopping.utility.ResponseStructure;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DiscountService {
    ResponseEntity<ResponseStructure<List<DiscountResponse>>> getDiscounts(Long productId);

    ResponseEntity<ResponseStructure<DiscountResponse>> updateDiscount(
            Long sellerId,
            Long discountId,
            DiscountRequest discountRequest);
}
