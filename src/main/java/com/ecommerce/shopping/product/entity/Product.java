package com.ecommerce.shopping.product.entity;

import com.ecommerce.shopping.discount.entity.Discount;
import com.ecommerce.shopping.enums.AvailabilityStatus;
import com.ecommerce.shopping.image.entity.Image;
import com.ecommerce.shopping.review.entity.Review;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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
    private String productTitle;
    private String productDescription;
    private double productPrice = 0;
    private int productQuantity = 0;
    @Enumerated(EnumType.STRING)
    private AvailabilityStatus availabilityStatus;

    @OneToMany(mappedBy = "product")
    private List<Discount> discounts;

    @OneToMany(mappedBy = "product")
    private List<Review> reviews;

    @OneToMany(mappedBy = "product")
    private List<Image> images;

}
