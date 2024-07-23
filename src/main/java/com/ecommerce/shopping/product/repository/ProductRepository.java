package com.ecommerce.shopping.product.repository;

import com.ecommerce.shopping.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
