package com.productManagement.IntegrationTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.productManagement.entity.Inventory;
import com.productManagement.entity.Product;
import com.productManagement.repository.InventoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class InventoryIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        inventoryRepository.deleteAll();

        Inventory inventory = new Inventory();
        Product product = new Product();
        product.setId(1L);
        inventory.setProduct(product);
        inventory.setOwnerName("Ali");
        inventory.setQuantity(10);

        inventoryRepository.save(inventory);
    }

    @Test
    void testIncreaseQuantity() throws Exception {
        mockMvc.perform(put("/api/inventory/1/increase")
                        .param("amount", "5"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/inventory/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity", is(15)));
    }

    @Test
    void testDecreaseQuantity() throws Exception {
        mockMvc.perform(put("/api/inventory/1/decrease")
                        .param("amount", "4"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/inventory/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity", is(6)));
    }

    @Test
    void testGetQuantity() throws Exception {
        mockMvc.perform(get("/api/inventory/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId", is(1)))
                .andExpect(jsonPath("$.owner", is("Ali")))
                .andExpect(jsonPath("$.quantity", is(10)));
    }

    @Test
    void testGetQuantity_NotFound() throws Exception {
        mockMvc.perform(get("/api/inventory/999"))
                .andExpect(status().isNotFound());
    }
}
