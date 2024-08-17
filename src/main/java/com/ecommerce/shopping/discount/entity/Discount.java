package com.ecommerce.shopping.discount.entity;


import com.ecommerce.shopping.enums.DiscountType;
import com.ecommerce.shopping.product.entity.Product;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long discountId;
    @Enumerated(EnumType.STRING)
    private DiscountType discountType;
    private double discountValue;
    private boolean isActive;
    @ManyToOne
    private Product product;

    public boolean getIsActive() {
        return this.isActive;
    }

}
