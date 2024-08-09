package com.ecommerce.shopping.discount.repository;

import com.ecommerce.shopping.discount.entity.Discount;
import com.ecommerce.shopping.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiscountRepository extends JpaRepository<Discount, Long> {

    List<Discount> findByProductAndIsActiveTrue(Product product);
}
