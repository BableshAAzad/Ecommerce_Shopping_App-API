package com.ecommerce.shopping.warehouse.dto;

import com.ecommerce.shopping.enums.DiscountType;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Inventory {
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
    private LocalDate updatedInventoryAt;
    private Long sellerId;
    private List<Stock> stocks;
    private double discount;
    private DiscountType discountType;
}
