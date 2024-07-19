package com.ecommerce.shopping.image.entity;

import com.ecommerce.shopping.enums.ImageType;
import com.ecommerce.shopping.product.entity.Product;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;
    private String image;
    private ImageType imageType;
    @ManyToOne
    private Product product;
}
