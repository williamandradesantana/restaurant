package io.github.williamandradesantana.restaurant.dtos;

import io.github.williamandradesantana.restaurant.domain.entity.ProductEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductResponse(
        Long id,
        String name,
        String description,
        BigDecimal price,
        Boolean available,
        Integer preparationTimeInMinutes,
        Long categoryId,
        String categoryName,
        LocalDateTime createdAt
) {
    public static ProductResponse fromEntity(ProductEntity product) {
        return new ProductResponse(
            product.getId(),
            product.getName(),
            product.getDescription(),
            product.getPrice(),
            product.getAvailable(),
            product.getPreparationTimeInMinutes(),
            product.getCategory().getId(),
            product.getCategory().getName(),
            product.getCreatedAt()
        );
    }
}
