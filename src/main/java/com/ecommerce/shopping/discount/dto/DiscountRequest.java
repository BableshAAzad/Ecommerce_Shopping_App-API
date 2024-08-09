package com.ecommerce.shopping.discount.dto;

import com.ecommerce.shopping.enums.DiscountType;
import lombok.Getter;

@Getter
public class DiscountRequest {
    private DiscountType discountType;
    private double discountValue;
    private boolean isActive;
}
