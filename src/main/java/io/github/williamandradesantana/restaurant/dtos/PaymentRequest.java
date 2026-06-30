package io.github.williamandradesantana.restaurant.dtos;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal value,
        String paymentMethod
) {
}
