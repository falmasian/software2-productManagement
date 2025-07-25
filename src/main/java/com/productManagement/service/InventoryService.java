package com.productManagement.service;

import com.productManagement.entity.Inventory;
import com.productManagement.entity.Product;
import com.productManagement.exception.NotFoundException;
import com.productManagement.repository.InventoryRepository;
import com.productManagement.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;

    public void increaseProductQuantity(Long productId, String ownerName, int amount) {
        Inventory inventory = getOrCreateInventory(productId, ownerName);
        inventory.setQuantity(inventory.getQuantity() + amount);
        inventoryRepository.save(inventory);
    }

    public void decreaseProductQuantity(Long productId, String ownerName, int amount) {
        Inventory inventory = getOrCreateInventory(productId, ownerName);
        if (inventory.getQuantity() < amount) {
            throw new IllegalArgumentException("Not enough inventory.");
        }
        inventory.setQuantity(inventory.getQuantity() - amount);
        inventoryRepository.save(inventory);
    }

    private Inventory getOrCreateInventory(Long productId, String ownerName) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        return inventoryRepository.findByProductAndOwnerName(product, ownerName)
                .orElseGet(() -> Inventory.builder()
                        .product(product)
                        .ownerName(ownerName)
                        .quantity(0)
                        .build());
    }

    public int getProductQuantity(Long productId, String ownerName) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        return inventoryRepository.findByProductAndOwnerName(product, ownerName)
                .map(Inventory::getQuantity)
                .orElse(0);
    }
}
