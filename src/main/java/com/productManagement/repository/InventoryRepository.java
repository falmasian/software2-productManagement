package com.productManagement.repository;

import com.productManagement.entity.Inventory;
import com.productManagement.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Optional<Inventory> findByProductAndOwnerName(Product product, String ownerName);
}
