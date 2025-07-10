package com.productManagement.controller;

import com.productManagement.entity.Product;
import com.productManagement.facade.ProductFacade;
import com.productManagement.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductController implements ProductFacade {

    private final ProductService productService;

    @Override
    public ResponseEntity<Product> createProduct(Product product) {
        return ResponseEntity.ok(productService.createProduct(product));
    }

    @Override
    public ResponseEntity<Product> updateProduct( Long id,Product product) {
        return ResponseEntity.ok(productService.updateProduct(id, product));
    }

    @Override
    public ResponseEntity<Void> deleteProduct( Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @Override
    public ResponseEntity<Product> getProductById( Long id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<List<Product>> searchProducts(
            String category,
         Double minPrice,
           Double maxPrice,
            Integer minStock
    ) {
        return ResponseEntity.ok(productService.searchProducts(category, minPrice, maxPrice, minStock));
    }
}
