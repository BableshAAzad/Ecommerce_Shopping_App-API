package com.ecommerce.shopping.order.entity;

import com.ecommerce.shopping.address.entity.Address;
import com.ecommerce.shopping.product.entity.Product;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "`order`")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    private int totalQuantity;
    private double totalPrice;
    private double discount;
    private double discountPrice;
    private double totalPayableAmount;
    @ManyToOne
    private Product product;
    @ManyToOne
    private Address address;
}
