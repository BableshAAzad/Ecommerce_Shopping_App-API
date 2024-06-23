package com.ecommerce.shopping.seller.repository;

import com.ecommerce.shopping.seller.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller, Long> {
}
