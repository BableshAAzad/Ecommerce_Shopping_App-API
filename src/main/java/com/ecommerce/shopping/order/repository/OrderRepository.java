package com.ecommerce.shopping.order.repository;

import com.ecommerce.shopping.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
