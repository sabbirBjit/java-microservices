package com.sabbir.microservices.inventory.repository;

import com.sabbir.microservices.inventory.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;


public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    boolean existsBySkuCodeAndQuantityGreaterThanEqual(String skuCode, int i);
}
