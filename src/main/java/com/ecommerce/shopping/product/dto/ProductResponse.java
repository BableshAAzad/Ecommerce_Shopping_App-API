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
public class ProductResponse {
    private Long productId;
    private String productTitle;
    private String productDescription;
    private double productPrice=0;
    private int productQuantity=0;
    @Enumerated(EnumType.STRING)
    private AvailabilityStatus availabilityStatus;
}
