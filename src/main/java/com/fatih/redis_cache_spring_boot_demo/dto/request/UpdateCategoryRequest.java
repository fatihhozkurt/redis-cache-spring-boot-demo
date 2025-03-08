package com.fatih.redis_cache_spring_boot_demo.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UpdateCategoryRequest(

        @NotNull
        UUID id,

        String categoryName,
        String categoryDescription
) {
}
