package com.sabbir.microservices.inventory.service;

import com.sabbir.microservices.inventory.model.Inventory;
import com.sabbir.microservices.inventory.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    public boolean isInStock(String skuCode, Integer quantity) {
        return inventoryRepository.existsBySkuCodeAndQuantityGreaterThanEqual(skuCode, quantity);
    }

    @Transactional
    public void updateInventoryAfterOrder(String skuCode, Integer orderedQuantity) {
        Inventory inventory = inventoryRepository.findBySkuCode(skuCode)
            .orElseThrow(() -> new RuntimeException("Inventory not found for SKU: " + skuCode));
        
        if (inventory.getQuantity() < orderedQuantity) {
            throw new RuntimeException("Insufficient inventory for SKU: " + skuCode);
        }
        
        inventory.setQuantity(inventory.getQuantity() - orderedQuantity);
        inventoryRepository.save(inventory);
        
        log.info("Inventory updated for SKU: {}, remaining quantity: {}", skuCode, inventory.getQuantity());
    }
}
