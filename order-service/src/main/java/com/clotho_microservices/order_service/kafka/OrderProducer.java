package com.clotho_microservices.order_service.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderProducer {

    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    public void sendOrderPlacedEvent(OrderPlacedEvent event) {
        kafkaTemplate.send("notificationTopic", event);
    }
}
