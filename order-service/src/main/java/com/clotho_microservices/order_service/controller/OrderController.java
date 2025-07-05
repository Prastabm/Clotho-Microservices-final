package com.clotho_microservices.order_service.controller;

import com.clotho_microservices.order_service.dto.OrderRequest;
import com.clotho_microservices.order_service.model.Order;
import com.clotho_microservices.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    @PostMapping
    public ResponseEntity<String> placeOrder(@RequestBody OrderRequest orderRequest) {
        try {
            orderService.placeOrder(orderRequest);
            return ResponseEntity.status(201).body("Order placed successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body("Failed to place order: " + e.getMessage());
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrderById(@PathVariable String orderId) {
        try {
            Order order = orderService.getOrderById(orderId);
            if (order != null) {
                return ResponseEntity.ok(order);
            } else {
                return ResponseEntity.status(404).body("Order not found with ID: " + orderId);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error retrieving order: " + e.getMessage());
        }
    }
}
