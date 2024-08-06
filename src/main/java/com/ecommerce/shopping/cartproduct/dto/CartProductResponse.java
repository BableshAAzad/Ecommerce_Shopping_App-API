package com.ecommerce.shopping.cartproduct.dto;

import com.ecommerce.shopping.product.dto.ProductResponse;
import com.ecommerce.shopping.product.entity.Product;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartProductResponse {
    private Long cartProductId;
    private int selectedQuantity;
    private ProductResponse product;
}
