package com.sabbir.microservices.inventory.repository;

import com.sabbir.microservices.inventory.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    boolean existsBySkuCodeAndQuantityGreaterThanEqual(String skuCode, int i);
    
    Optional<Inventory> findBySkuCode(String skuCode);
}
