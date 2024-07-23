package com.ecommerce.shopping.product.entity;

import com.ecommerce.shopping.enums.AvailabilityStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    @Id
    private Long productId;
    private String productTitle;
    private String productDescription;
    private double productPrice=0;
    private int productQuantity=0;
    @Enumerated(EnumType.STRING)
    private AvailabilityStatus availabilityStatus;
}
