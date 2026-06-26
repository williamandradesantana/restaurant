package io.github.williamandradesantana.restaurant.dtos;

import io.github.williamandradesantana.restaurant.domain.entity.OrderItemEntity;
import io.github.williamandradesantana.restaurant.domain.enums.OrderItemStatus;

import java.math.BigDecimal;

public record KitchenItemResponse(
        Long orderItemId,
        Long orderId,
        Integer tableNumber,
        String productName,
        Integer quantity,
        String observation,
        BigDecimal unitPrice,
        OrderItemStatus status
) {
    public static KitchenItemResponse fromEntity(OrderItemEntity orderItem) {
        return new KitchenItemResponse(
              orderItem.getId(),
              orderItem.getOrder().getId(),
              orderItem.getOrder().getTable().getNumber(),
              orderItem.getProduct().getName(),
              orderItem.getQuantity(),
              orderItem.getObservation(),
              orderItem.getUnitPrice(),
              orderItem.getStatus()
        );
    }
}
