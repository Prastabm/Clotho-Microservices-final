package com.clotho_microservices.order_service.service;

import com.clotho_microservices.order_service.client.InventoryClient;
import com.clotho_microservices.order_service.dto.OrderRequest;
import com.clotho_microservices.order_service.kafka.OrderPlacedEvent;
import com.clotho_microservices.order_service.model.Order;
import com.clotho_microservices.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    public OrderService(OrderRepository orderRepository,
                        InventoryClient inventoryClient,
                        KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate) {
        this.orderRepository = orderRepository;
        this.inventoryClient = inventoryClient;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void placeOrder(OrderRequest orderRequest) {
        log.info("Placing order for productId={}, quantity={}", orderRequest.productId(), orderRequest.quantity());

        boolean isProductAvailable = inventoryClient.isAvailable(
                orderRequest.productId(),
                orderRequest.quantity()
        );

        if (!isProductAvailable) {
            throw new RuntimeException("Product is not available in inventory");
        }

        Order order = createOrder(orderRequest);
        orderRepository.save(order);

        OrderPlacedEvent event = buildOrderPlacedEvent(orderRequest, order.getOrderId());
        kafkaTemplate.send("orderplaced", event);
        log.info("Published OrderPlacedEvent to Kafka: {}", event);
    }

    public Order getOrderById(String orderId) {
        return orderRepository.findByOrderId(orderId);
    }

    private Order createOrder(OrderRequest request) {
        return Order.builder()
                .orderId(UUID.randomUUID().toString())
                .productId(request.productId())
                .quantity(request.quantity())
                .customerId(request.customerId())
                .shippingAddress(request.shippingAddress())
                .amount(request.totalPrice())
                .build();
    }

    private OrderPlacedEvent buildOrderPlacedEvent(OrderRequest request, String orderId) {
        return OrderPlacedEvent.builder()
                .orderNumber(orderId)
                .email(request.userDetails().email())
                .firstName(request.userDetails().firstName())
                .lastName(request.userDetails().lastName())
                .build();
    }
}
