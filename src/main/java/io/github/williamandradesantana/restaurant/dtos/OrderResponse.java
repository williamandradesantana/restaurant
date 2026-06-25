package io.github.williamandradesantana.restaurant.dtos;

import io.github.williamandradesantana.restaurant.domain.entity.OrderEntity;
import io.github.williamandradesantana.restaurant.domain.enums.OrderStatus;

import java.time.LocalDateTime;

public record OrderResponse(
        Long id,
        Long tableId,
        Integer tableNumber,
        LocalDateTime openingDate,
        LocalDateTime closingDate,
        OrderStatus status,
        String observation
) {
    public static OrderResponse fromEntity(OrderEntity order) {
        return new OrderResponse(
            order.getId(),
            order.getTable().getId(),
            order.getTable().getNumber(),
            order.getOpeningDate(),
            order.getClosingDate(),
            order.getStatus(),
            order.getObservation()
        );
    }
}
