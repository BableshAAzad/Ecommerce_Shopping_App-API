package com.ecommerce.shopping.cartproduct.service.impl;

import com.ecommerce.shopping.cartproduct.dto.CartProductRequest;
import com.ecommerce.shopping.cartproduct.dto.CartProductResponse;
import com.ecommerce.shopping.cartproduct.mapper.CartProductMapper;
import com.ecommerce.shopping.cartproduct.repository.CartProductRepository;
import com.ecommerce.shopping.cartproduct.service.CartProductService;
import com.ecommerce.shopping.utility.ResponseStructure;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CartProductServiceImpl implements CartProductService {

   private final CartProductMapper cartProductMapper;
   private final CartProductRepository cartProductRepository;

    @Override
    public ResponseEntity<ResponseStructure<CartProductResponse>> addProductInCart(CartProductRequest cartProductRequest) {

        return null;
    }
}
