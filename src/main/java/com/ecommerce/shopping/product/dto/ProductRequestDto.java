package com.ecommerce.shopping.product.dto;

import com.ecommerce.shopping.enums.DiscountType;
import com.ecommerce.shopping.enums.MaterialType;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProductRequestDto {
    private Long productId;
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
    private double discount;
    private DiscountType discountType;
}
