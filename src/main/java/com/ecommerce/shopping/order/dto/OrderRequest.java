package com.ecommerce.shopping.order.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequest {
    private int totalQuantity;
    private double totalPrice;
    private double discountPrice;
    private double totalPayableAmount;
}
