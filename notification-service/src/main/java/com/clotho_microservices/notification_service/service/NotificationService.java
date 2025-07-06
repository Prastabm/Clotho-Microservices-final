package com.clotho_microservices.notification_service.service;



import com.clotho_microservices.notification_service.kafka.OrderPlacedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationService {

    private final JavaMailSender mailSender;

    // ✅ Manual constructor injection
    public NotificationService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @KafkaListener(topics = "orderplaced", groupId = "notification-group")
    public void listen(OrderPlacedEvent orderPlacedEvent) {
        log.info("Received order placed event: {}", orderPlacedEvent);

        if (orderPlacedEvent.getEmail() == null || orderPlacedEvent.getEmail().isBlank()) {
            log.warn("Email is null or empty for order: {}", orderPlacedEvent.getOrderNumber());
            return;
        }

        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setFrom("clotho@gmail.com");
            helper.setTo(orderPlacedEvent.getEmail());
            helper.setSubject("Order Placed");
            helper.setText("Your order with number " + orderPlacedEvent.getOrderNumber() + " has been placed successfully.", true);
        };

        try {
            mailSender.send(messagePreparator);
            log.info("Email sent successfully to {}", orderPlacedEvent.getEmail());
        } catch (Exception e) {
            log.error("Failed to send email to {}", orderPlacedEvent.getEmail(), e);
        }
    }
}

