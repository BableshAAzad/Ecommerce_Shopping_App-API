package com.ecommerce.shopping.cartproduct.controller;

import com.ecommerce.shopping.cartproduct.dto.CartProductRequest;
import com.ecommerce.shopping.cartproduct.dto.CartProductResponse;
import com.ecommerce.shopping.cartproduct.service.CartProductService;
import com.ecommerce.shopping.utility.ResponseStructure;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class CartProductController {

      private final CartProductService cartProductService;

      private ResponseEntity<ResponseStructure<CartProductResponse>> addToProductInCart(@RequestBody CartProductRequest cartProductRequest){
          return cartProductService.addProductInCart(cartProductRequest);
      }
}
