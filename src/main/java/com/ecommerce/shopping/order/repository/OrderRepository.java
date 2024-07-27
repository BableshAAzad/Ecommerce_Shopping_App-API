package com.ecommerce.shopping.order.repository;

import com.ecommerce.shopping.customer.entity.Customer;
import com.ecommerce.shopping.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
