package com.productManagement.facade;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

public interface InventoryFacade {

    @Operation(summary = "افزایش موجودی یک محصول", responses = {
            @ApiResponse(responseCode = "200", description = "موجودی با موفقیت افزایش یافت"),
            @ApiResponse(responseCode = "404", description = "موردی یافت نشد")
    })
    @PostMapping("/inventory/increase/{id}")
    void increaseInventory(@Parameter(description = "شناسه محصول") @PathVariable Long id,
                           @Parameter(description = "نام صاحب محصول") @RequestParam String ownerName,
                           @Parameter(description = "مقدار افزایش موجودی") @RequestParam int amount);

    @Operation(summary = "کاهش موجودی یک محصول", responses = {
            @ApiResponse(responseCode = "200", description = "موجودی با موفقیت کاهش یافت"),
            @ApiResponse(responseCode = "400", description = "مقدار کاهش بیشتر از موجودی بود"),
            @ApiResponse(responseCode = "404", description = "موردی یافت نشد")
    })
    @PostMapping("/inventory/decrease/{id}")
    void decreaseInventory(@Parameter(description = "شناسه محصول") @PathVariable Long id,
                           @Parameter(description = "نام صاحب محصول") @RequestParam String ownerName,
                           @Parameter(description = "مقدار کاهش موجودی") @RequestParam int amount);

    @Operation(summary = "استعلام موجودی یک محصول برای یک صاحب خاص", responses = {
            @ApiResponse(responseCode = "200", description = "موجودی با موفقیت بازیابی شد"),
            @ApiResponse(responseCode = "404", description = "محصول یا صاحب محصول یافت نشد")
    })
    @GetMapping("/inventory/{id}")
    int getInventory(@Parameter(description = "شناسه محصول") @PathVariable Long id,
                     @Parameter(description = "نام صاحب محصول") @RequestParam String ownerName);
}
