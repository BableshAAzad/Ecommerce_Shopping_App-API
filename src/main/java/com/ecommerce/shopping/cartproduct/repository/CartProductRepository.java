package com.ecommerce.shopping.cartproduct.repository;

import com.ecommerce.shopping.cartproduct.entity.CartProduct;
import com.ecommerce.shopping.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartProductRepository  extends JpaRepository<CartProduct, Long> {
    List<CartProduct> findByProduct(Product product);
}
