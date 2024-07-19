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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String productDescription;
    private int productPrice=0;
    private int productQuantity=0;
    private AvailabilityStatus availabilityStatus;
}
