package com.ecommerce.shopping.cartproduct.service;

import com.ecommerce.shopping.cartproduct.dto.CartProductRequest;
import com.ecommerce.shopping.cartproduct.dto.CartProductResponse;
import com.ecommerce.shopping.utility.ResponseStructure;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CartProductService {

    ResponseEntity<ResponseStructure<CartProductResponse>> addProductInCart(
            CartProductRequest cartProductRequest, Long customerId);

    ResponseEntity<ResponseStructure<CartProductResponse>> updateCartProduct(
            int selectedQuantity, Long cartProductId);

    ResponseEntity<ResponseStructure<CartProductResponse>> removeCartProduct(
            Long customerId,  Long cartProductId);

    ResponseEntity<ResponseStructure<List<CartProductResponse>>> getCartProducts(Long customerId);

    ResponseEntity<ResponseStructure<String>> removeAllCartProduct(Long customerId);
}
