package com.clotho_microservices.order_service.client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "inventory", url = "${inventory.url}")
public interface InventoryClient {

    @GetMapping("/api/inventory")
    boolean isAvailable(@RequestParam String productId, @RequestParam int quantity);
}
