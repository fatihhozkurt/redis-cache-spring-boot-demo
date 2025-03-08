package com.fatih.redis_cache_spring_boot_demo.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateProductRequest(

        @NotNull
        UUID categoryId,

        @NotNull
        @NotBlank
        @Length(min = 1, max = 255)
        String productName,

        @NotNull
        @NotBlank
        @Length(min = 20, max = 500)
        String productDescription,

        @NotNull
        @Min(value = 0)
        @Max(value = 10000)
        Integer stockQuantity,

        @NotNull
        @Min(value = 0)
        @Max(value = 100000)
        BigDecimal price
) {
}
