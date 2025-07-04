package com.clotho_microservices.product_service.dto;

public record ProductRequest(String id, String name, String description,
                             String price, String category, String imageUrl) {
    // This record can be used to transfer product data in requests
    // Additional validation or transformation logic can be added if needed
}
