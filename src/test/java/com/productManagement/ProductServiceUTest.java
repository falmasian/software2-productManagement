package com.productManagement;

import com.productManagement.entity.Product;
import com.productManagement.repository.ProductRepository;
import com.productManagement.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceUTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void testCreateProduct_success() {
        Product product = new Product(1L,"Milk", "Dairy", 2.5, 10, true);
        when(productRepository.save(product)).thenReturn(product);

        Product result = productService.createProduct(product);

        assertEquals("Milk", result.getName());
        verify(productRepository).save(product);
    }

    @Test
    void testUpdateProduct_success() {
        Product existing = new Product(1L,"Milk", "Dairy", 2.5, 10, true);
        Product updated = new Product(1L,"Skimmed Milk", "Dairy", 3.0, 15, true);

        when(productRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(productRepository.save(Mockito.any(Product.class))).thenReturn(updated);

        Product result = productService.updateProduct(1L, updated);

        assertEquals("Skimmed Milk", result.getName());
        assertEquals(15, result.getStock());
    }

    @Test
    void testDeleteProduct_success() {
        Product product = new Product(2L, "Bread", "Bakery", 1.0, 20,true);
        productService.deleteProduct(2L);
        verify(productRepository).deleteById(2L);
    }


    @Test
    void testSearchByCategory() {
        List<Product> products = List.of(
                new Product(3L, "Cheese", "Dairy", 5.0, 5, true
                ),
                new Product(4L, "Yogurt", "Dairy", 2.0, 10, true)
        );
        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = productService.searchProducts("Dairy", null, null, null);

        assertEquals(2, result.size());
    }

    @Test
    void testSearchByPriceRange() {
        List<Product> products = List.of(
                new Product(1L, "Apple", "Fruit", 3.0, 30, true)
        );
        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = productService.searchProducts(null, 2.0, 4.0, null);

        assertEquals(1, result.size());
        assertEquals("Apple", result.get(0).getName());
    }

    @Test
    void testSearchByStock() {
        List<Product> products = List.of(
                new Product(1L, "Rice", "Grain", 10.0, 50, true)
        );
        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = productService.searchProducts(null, null, null, 40);

        assertEquals(1, result.size());
        assertTrue(result.get(0).getStock() >= 40);
    }
}
