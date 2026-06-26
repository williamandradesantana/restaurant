package io.github.williamandradesantana.restaurant.dtos;

import io.github.williamandradesantana.restaurant.domain.entity.OrderItemEntity;
import io.github.williamandradesantana.restaurant.domain.enums.OrderItemStatus;

import java.math.BigDecimal;

public record OrderItemResponse(
        Long id,
        Long orderId,
        Long productId,
        String productName,
        Integer quantity,
        BigDecimal unitPrice,
        BigDecimal total,
        String observation,
        OrderItemStatus status
) {
    public static OrderItemResponse fromEntity(OrderItemEntity orderItem) {
        BigDecimal total = orderItem.getUnitPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity()));

        return new OrderItemResponse(
            orderItem.getId(),
            orderItem.getOrder().getId(),
            orderItem.getProduct().getId(),
            orderItem.getProduct().getName(),
            orderItem.getQuantity(),
            orderItem.getUnitPrice(),
            total,
            orderItem.getObservation(),
            orderItem.getStatus()
        );
    }
}
