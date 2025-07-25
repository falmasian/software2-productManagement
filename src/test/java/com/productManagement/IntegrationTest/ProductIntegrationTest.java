package com.productManagement.IntegrationTest;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.productManagement.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.is;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    Product sampleProduct() {
        return Product.builder()
                .name("تست محصول")
                .category("کتاب")
                .price(100.0)
                .build();
    }

    @Test
    void createProduct_shouldReturn200() throws Exception {
        Product product = sampleProduct();

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value(product.getName()));
    }

    @Test
    void getAllProducts_shouldReturnList() throws Exception {
        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void getProductById_shouldReturnProduct() throws Exception {
        Product product = sampleProduct();
        String response = mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andReturn().getResponse().getContentAsString();
        Product created = objectMapper.readValue(response, Product.class);

        mockMvc.perform(get("/api/products/" + created.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(created.getId()));
    }

    @Test
    void updateProduct_shouldUpdateAndReturn() throws Exception {
        Product product = sampleProduct();
        String response = mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andReturn().getResponse().getContentAsString();
        Product created = objectMapper.readValue(response, Product.class);
        created.setPrice(200.0);

        mockMvc.perform(put("/api/products/" + created.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(created)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(200.0));
    }

    @Test
    void createProduct_success() throws Exception {
        Product product = Product.builder()
                .name("ماگ حرارتی")
                .category("لوازم خانه")
                .price(85000.0)
                .stock(20)
                .build();

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("ماگ حرارتی")))
                .andExpect(jsonPath("$.category", is("لوازم خانه")))
                .andExpect(jsonPath("$.price", is(85000.0)))
                .andExpect(jsonPath("$.stock", is(20)));
    }

    @Test
    void deleteProduct_shouldDelete() throws Exception {
        Product product = sampleProduct();
        String response = mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andReturn().getResponse().getContentAsString();
        Product created = objectMapper.readValue(response, Product.class);

        mockMvc.perform(delete("/api/products/" + created.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void searchProducts_shouldReturnMatchingProducts() throws Exception {
        mockMvc.perform(get("/api/products/search")
                        .param("category", "کتاب")
                        .param("minPrice", "50")
                        .param("maxPrice", "150"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}

