package io.github.williamandradesantana.restaurant.dtos;

import io.github.williamandradesantana.restaurant.domain.entity.AccountClosingEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AccountClosingResponse(
        Long id,
        Long orderId,
        Integer tableNumber,
        BigDecimal subtotal,
        BigDecimal serviceFee,
        BigDecimal discount,
        BigDecimal total,
        LocalDateTime closingDate
) {
    public static AccountClosingResponse fromEntity(AccountClosingEntity accountClosing) {
        return new AccountClosingResponse(
                accountClosing.getId(),
                accountClosing.getOrder().getId(),
                accountClosing.getOrder().getTable().getNumber(),
                accountClosing.getSubtotal(),
                accountClosing.getServiceFee(),
                accountClosing.getDiscount(),
                accountClosing.getTotal(),
                accountClosing.getClosingDate()
        );
    }
}
