package com.ecommerce.shopping.cartproduct.repository;

import com.ecommerce.shopping.cartproduct.entity.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartProductRepository  extends JpaRepository<CartProduct, Long> {
}
