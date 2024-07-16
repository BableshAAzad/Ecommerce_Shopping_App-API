package com.ecommerce.shopping.product.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product {
    private Long inventoryId;
    private String productTitle;
    private double lengthInMeters;
    private double breadthInMeters;
    private double heightInMeters;
    private double weightInKg;
    private double price;
    private String description;
    private String productImage;
    private List<String> materialTypes;
    private LocalDate restockedAt;
    private Long sellerId;
    private List<Stock> stocks;
}
