package com.ecommerce.shopping.seller.repository;

import com.ecommerce.shopping.address.entity.Address;
import com.ecommerce.shopping.seller.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SellerRepository extends JpaRepository<Seller, Long> {

}
