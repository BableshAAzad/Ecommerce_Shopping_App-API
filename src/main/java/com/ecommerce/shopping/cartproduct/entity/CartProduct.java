package com.ecommerce.shopping.cartproduct.entity;

import com.ecommerce.shopping.product.entity.Product;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartProductId;
    private int selectedQuantity;
    @ManyToOne
    private Product product;
}
