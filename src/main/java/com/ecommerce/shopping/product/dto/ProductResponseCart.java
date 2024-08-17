package com.ecommerce.shopping.product.dto;

import com.ecommerce.shopping.enums.AvailabilityStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponseCart {
    private Long productId;
    private String productTitle;
    private String productDescription;
    private double productPrice;
    private int productQuantity;
    @Enumerated(EnumType.STRING)
    private AvailabilityStatus availabilityStatus;
    private String productImage;
    private double discount;
}
