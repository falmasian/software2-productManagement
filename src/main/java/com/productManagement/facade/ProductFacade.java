package com.productManagement.facade;

import com.productManagement.entity.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;

public interface ProductFacade {

    @Operation(summary = "ایجاد یک محصول جدید", responses = {
            @ApiResponse(responseCode = "200", description = "محصول با موفقیت ایجاد شد")
    })
    @PostMapping
    ResponseEntity<Product> createProduct(@Parameter(description = "اطلاعات محصول برای ایجاد")
                                          @RequestBody Product product);

    @Operation(summary = "ویرایش محصول", responses = {
            @ApiResponse(responseCode = "200", description = "محصول با موفقیت ویرایش شد"),
            @ApiResponse(responseCode = "404", description = "محصول پیدا نشد")
    })
    @PutMapping("/{id}")
    ResponseEntity<Product> updateProduct(@Parameter(description = "شناسه محصول") @PathVariable Long id,
    @Parameter(description = "اطلاعات جدید محصول") @RequestBody Product product);

    @Operation(summary = "حذف محصول", responses = {
            @ApiResponse(responseCode = "204", description = "محصول حذف شد")
    })
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteProduct(@Parameter(description = "شناسه محصول برای حذف") @PathVariable Long id);

    @Operation(summary = "دریافت لیست تمام محصولات", responses = {
            @ApiResponse(responseCode = "200", description = "لیست محصولات با موفقیت بازیابی شد")
    })
    @GetMapping
    ResponseEntity<List<Product>> getAllProducts();

    @Operation(summary = "دریافت اطلاعات یک محصول با شناسه", responses = {
            @ApiResponse(responseCode = "200", description = "محصول با موفقیت بازیابی شد"),
            @ApiResponse(responseCode = "404", description = "محصولی با این شناسه پیدا نشد")
    })
    @GetMapping("/{id}")
    ResponseEntity<Product> getProductById(@Parameter(description = "شناسه محصول") @PathVariable Long id);

    @Operation(summary = "جستجو با فیلتر", description = "فیلتر بر اساس دسته، قیمت و موجودی")
    @GetMapping("/search")
    ResponseEntity<List<Product>> searchProducts(
            @Parameter(description = "فیلتر دسته") @RequestParam(required = false) String category,
            @Parameter(description = "حداقل قیمت") @RequestParam(required = false) Double minPrice,
            @Parameter(description = "حداکثر قیمت") @RequestParam(required = false) Double maxPrice,
            @Parameter(description = "حداقل موجودی") @RequestParam(required = false) Integer minStock
    );
}