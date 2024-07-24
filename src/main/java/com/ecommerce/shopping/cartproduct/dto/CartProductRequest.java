package com.ecommerce.shopping.cartproduct.dto;

import com.ecommerce.shopping.product.entity.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartProductRequest {
    private int selectedQuantity;
    private Product product;
}
