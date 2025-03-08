package com.fatih.redis_cache_spring_boot_demo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record CreateCategoryRequest(

        @NotNull
        @NotBlank
        @Length(min = 3, max = 30)
        String categoryName,

        @NotNull
        @NotBlank
        @Length(min = 20, max = 500)
        String categoryDescription
) {
}
