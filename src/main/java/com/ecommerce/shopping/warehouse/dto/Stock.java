package com.ecommerce.shopping.warehouse.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Stock {
    private Long stockId;
    private int quantity;
}
