package io.github.williamandradesantana.restaurant.dtos;

public record OrderItemRequest(
        Long productId,
        Integer quantity,
        String observation
) {
}
