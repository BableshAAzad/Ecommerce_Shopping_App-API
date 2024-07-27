package com.ecommerce.shopping.cartproduct.mapper;

import com.ecommerce.shopping.cartproduct.dto.CartProductRequest;
import com.ecommerce.shopping.cartproduct.dto.CartProductResponse;
import com.ecommerce.shopping.cartproduct.entity.CartProduct;
import org.springframework.stereotype.Component;

@Component
public class CartProductMapper {

    public CartProduct mapCartProductRequestToCartProduct(CartProductRequest cartProductRequest, CartProduct cartProduct) {
        cartProduct.setSelectedQuantity(cartProductRequest.getSelectedQuantity());
        cartProduct.setProduct(cartProductRequest.getProduct());
        return cartProduct;
    }

    public CartProductResponse mapCartProductToCartProductResponse(CartProduct cartProduct) {
        return CartProductResponse.builder()
                .cartProductId(cartProduct.getCartProductId())
                .selectedQuantity(cartProduct.getSelectedQuantity())
                .product(cartProduct.getProduct())
                .build();
    }

}
