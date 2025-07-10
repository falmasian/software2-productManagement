package com.productManagement.repository;

import com.productManagement.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryContainingIgnoreCase(String category);
    List<Product> findByPriceBetween(Double min, Double max);
    List<Product> findByStockGreaterThan(Integer stock);
}
