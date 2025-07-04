package com.clotho_microservices.product_service.repository;

import com.clotho_microservices.product_service.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
    // You can add custom queries here if needed
}
