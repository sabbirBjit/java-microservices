package com.sabbir.microservices.inventory.event;

import com.sabbir.microservices.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class InventoryEventListener {

    private final InventoryService inventoryService;

    @KafkaListener(topics = "order-placed", groupId = "inventory-service")
    public void handleOrderPlacedEvent(OrderPlacedEvent event) {
        log.info("Received order placed event: {}", event);
        
        try {
            // Update inventory quantities
            inventoryService.updateInventoryAfterOrder(event.skuCode(), event.quantity());
            log.info("Inventory updated for SKU: {} with quantity: {}", event.skuCode(), event.quantity());
        } catch (Exception e) {
            log.error("Error updating inventory for order: {}", event.orderNumber(), e);
        }
    }
}
