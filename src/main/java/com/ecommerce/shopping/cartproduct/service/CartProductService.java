package com.ecommerce.shopping.cartproduct.service;

import com.ecommerce.shopping.cartproduct.dto.CartProductRequest;
import com.ecommerce.shopping.cartproduct.dto.CartProductResponse;
import com.ecommerce.shopping.utility.ResponseStructure;
import org.springframework.http.ResponseEntity;

public interface CartProductService {
    ResponseEntity<ResponseStructure<CartProductResponse>> addProductInCart(CartProductRequest cartProductRequest);
}
