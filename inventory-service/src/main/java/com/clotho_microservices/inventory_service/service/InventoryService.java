package com.clotho_microservices.inventory_service.service;

import com.clotho_microservices.inventory_service.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }
    public boolean isAvailable(String productId, Integer quantity) {
        return inventoryRepository.existsByProductIdAndQuantityGreaterThanEqual(productId, quantity);
    }
}