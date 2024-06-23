package com.ecommerce.shopping.customer.repository;

import com.ecommerce.shopping.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
