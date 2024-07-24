package com.ecommerce.shopping.order.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class OrderResponseDto {
    private Long orderId;
    private String inventoryTitle;
    private String inventoryImage;
    private String invoiceLink;
    private LocalDate invoiceDate;
}
