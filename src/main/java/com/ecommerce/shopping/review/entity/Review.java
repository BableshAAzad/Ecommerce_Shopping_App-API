package com.ecommerce.shopping.review.entity;

import com.ecommerce.shopping.customer.entity.Customer;
import com.ecommerce.shopping.product.entity.Product;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;
    private int rating;
    private String review;
    @ManyToOne
    private Product product;
    @ManyToOne
    private Customer customer;
}
