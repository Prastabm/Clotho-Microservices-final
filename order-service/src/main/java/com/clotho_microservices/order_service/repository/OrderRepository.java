package com.clotho_microservices.order_service.repository;

import com.clotho_microservices.order_service.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
public interface OrderRepository extends JpaRepository<Order, String> {
    Order findByOrderId(String orderId);
}