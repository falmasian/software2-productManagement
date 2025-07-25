package com.productManagement;

import com.productManagement.entity.Inventory;
import com.productManagement.entity.Product;
import com.productManagement.exception.NotFoundException;
import com.productManagement.repository.InventoryRepository;
import com.productManagement.repository.ProductRepository;
import com.productManagement.service.InventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class InventoryServiceUTest {

    private InventoryRepository inventoryRepository;
    private ProductRepository productRepository;
    private InventoryService inventoryService;

    @BeforeEach
    void setUp() {
        inventoryRepository = mock(InventoryRepository.class);
        productRepository = mock(ProductRepository.class);
        inventoryService = new InventoryService(inventoryRepository, productRepository);
    }

    @Test
    void testIncreaseProductQuantity_existingInventory() {
        Product product = new Product(1L, "Phone", "Electronics", 500.0, 10, true);
        Inventory inventory = Inventory.builder().product(product).ownerName("Ali").quantity(5).build();

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(inventoryRepository.findByProductAndOwnerName(product, "Ali")).thenReturn(Optional.of(inventory));

        inventoryService.increaseProductQuantity(1L, "Ali", 3);

        assertEquals(8, inventory.getQuantity());
        verify(inventoryRepository).save(inventory);
    }

    @Test
    void testDecreaseProductQuantity_success() {
        Product product = new Product(2L, "Laptop", "Electronics", 1000.0, 5, true);
        Inventory inventory = Inventory.builder().product(product).ownerName("Sara").quantity(10).build();

        when(productRepository.findById(2L)).thenReturn(Optional.of(product));
        when(inventoryRepository.findByProductAndOwnerName(product, "Sara")).thenReturn(Optional.of(inventory));

        inventoryService.decreaseProductQuantity(2L, "Sara", 4);

        assertEquals(6, inventory.getQuantity());
        verify(inventoryRepository).save(inventory);
    }

    @Test
    void testDecreaseProductQuantity_notEnough() {
        Product product = new Product(3L, "TV", "Electronics", 2000.0, 2, true);
        Inventory inventory = Inventory.builder().product(product).ownerName("Reza").quantity(3).build();

        when(productRepository.findById(3L)).thenReturn(Optional.of(product));
        when(inventoryRepository.findByProductAndOwnerName(product, "Reza")).thenReturn(Optional.of(inventory));

        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                inventoryService.decreaseProductQuantity(3L, "Reza", 5));

        assertEquals("Not enough inventory.", ex.getMessage());
        verify(inventoryRepository, never()).save(any());
    }

    @Test
    void testGetProductQuantity_inventoryExists() {
        Product product = new Product(4L, "Monitor", "Electronics", 300.0, 3, true);
        Inventory inventory = Inventory.builder().product(product).ownerName("Mina").quantity(7).build();

        when(productRepository.findById(4L)).thenReturn(Optional.of(product));
        when(inventoryRepository.findByProductAndOwnerName(product, "Mina")).thenReturn(Optional.of(inventory));

        int quantity = inventoryService.getProductQuantity(4L, "Mina");

        assertEquals(7, quantity);
    }

    @Test
    void testGetProductQuantity_inventoryNotExists() {
        Product product = new Product(5L, "Tablet", "Electronics", 250.0, 2, true);

        when(productRepository.findById(5L)).thenReturn(Optional.of(product));
        when(inventoryRepository.findByProductAndOwnerName(product, "Omid")).thenReturn(Optional.empty());

        int quantity = inventoryService.getProductQuantity(5L, "Omid");

        assertEquals(0, quantity);
    }

    @Test
    void testIncreaseProductQuantity_productNotFound() {
        when(productRepository.findById(10L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () ->
                inventoryService.increaseProductQuantity(10L, "Someone", 3));
    }
}
