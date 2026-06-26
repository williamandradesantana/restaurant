package io.github.williamandradesantana.restaurant.dtos;

public record OrderRequest(
        Long tableId,
        String observation
) {
}
