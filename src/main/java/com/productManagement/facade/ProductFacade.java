package com.productManagement.facade;


import com.productManagement.entity.Product;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface ProductFacade {


    @PostMapping
    ResponseEntity<Product> createProduct(@RequestBody Product product);

    @PutMapping("/{id}")
     ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product);

    @DeleteMapping("/{id}")
     ResponseEntity<Void> deleteProduct(@PathVariable Long id);

    @GetMapping
     ResponseEntity<List<Product>> getAllProducts();

    @GetMapping("/{id}")
     ResponseEntity<Product> getProductById(@PathVariable Long id);

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Integer minStock
    );
}