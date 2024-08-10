package com.ecommerce.shopping.cartproduct.dto;

import com.ecommerce.shopping.product.dto.ProductResponseCart;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartProductResponse {
    private Long cartProductId;
    private int selectedQuantity;
    private ProductResponseCart product;
}
