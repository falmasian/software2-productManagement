package com.productManagement.controller;

import com.productManagement.facade.InventoryFacade;
import com.productManagement.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/inventory")
public class InventoryController implements InventoryFacade{

    private InventoryService inventoryService;

    @Override
    public void increaseInventory(Long id, String ownerName, int amount) {
        inventoryService.increaseProductQuantity(id, ownerName, amount);
    }

    @Override
    public void decreaseInventory(Long id, String ownerName, int amount) {
        inventoryService.decreaseProductQuantity(id, ownerName, amount);
    }

    @Override
    public int getInventory(Long id, String ownerName) {
        return inventoryService.getProductQuantity(id, ownerName);
    }
}
