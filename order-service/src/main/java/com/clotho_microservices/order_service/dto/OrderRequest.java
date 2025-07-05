package com.clotho_microservices.order_service.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record OrderRequest(
        String productId,
        @NotNull
        Integer quantity,
        String customerId,
        UserDetails userDetails,
        String shippingAddress,
        BigDecimal totalPrice
) {
    public record UserDetails(String email, String firstName, String lastName) {
    }
}
