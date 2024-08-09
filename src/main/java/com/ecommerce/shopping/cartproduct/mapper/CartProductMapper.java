package com.ecommerce.shopping.cartproduct.mapper;

import com.ecommerce.shopping.cartproduct.dto.CartProductRequest;
import com.ecommerce.shopping.cartproduct.dto.CartProductResponse;
import com.ecommerce.shopping.cartproduct.entity.CartProduct;
import com.ecommerce.shopping.product.mapper.ProductMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CartProductMapper {
    private final ProductMapper productMapper;

    public CartProduct mapCartProductRequestToCartProduct(CartProductRequest cartProductRequest, CartProduct cartProduct) {
        cartProduct.setSelectedQuantity(cartProductRequest.getSelectedQuantity());
        cartProduct.setProduct(cartProductRequest.getProduct());
        return cartProduct;
    }

    public CartProductResponse mapCartProductToCartProductResponse(CartProduct cartProduct) {
        return CartProductResponse.builder()
                .cartProductId(cartProduct.getCartProductId())
                .selectedQuantity(cartProduct.getSelectedQuantity())
                .product(productMapper.mapProductToProductResponseCart(cartProduct.getProduct()))
                .build();
    }

}
