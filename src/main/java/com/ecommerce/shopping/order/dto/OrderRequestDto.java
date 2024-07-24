package com.ecommerce.shopping.order.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequestDto {
    private Long orderId;
    private Long customerId;
    private int totalQuantity;
    private double totalPrice;
    private double discountPrice;
    private double totalPayableAmount;

    private AddressDto addressDto;
}
