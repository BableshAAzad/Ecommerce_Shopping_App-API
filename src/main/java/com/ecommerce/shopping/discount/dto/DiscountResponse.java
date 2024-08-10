package com.ecommerce.shopping.discount.dto;

import com.ecommerce.shopping.enums.DiscountType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DiscountResponse {
    private Long discountId;
    private DiscountType discountType;
    private double discountValue;
    private boolean isActive;
}
