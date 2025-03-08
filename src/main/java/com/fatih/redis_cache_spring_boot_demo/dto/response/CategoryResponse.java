package com.fatih.redis_cache_spring_boot_demo.dto.response;

import java.util.UUID;

public record CategoryResponse(

        UUID id,
        String categoryName,
        String categoryDescription
) {
}
