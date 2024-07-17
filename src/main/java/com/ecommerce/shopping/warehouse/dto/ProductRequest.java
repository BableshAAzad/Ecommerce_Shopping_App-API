package com.ecommerce.shopping.warehouse.dto;

import com.ecommerce.shopping.enums.MaterialType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private Long sellerId;
    private String productTitle;
    private double lengthInMeters;
    private double breadthInMeters;
    private double heightInMeters;
    private double weightInKg;
    private double price;
    private String description;
    private String productImage;
    private List<MaterialType> materialTypes;
}
