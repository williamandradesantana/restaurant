package io.github.williamandradesantana.restaurant.dtos;

import java.math.BigDecimal;

public record AccountClosingRequest(
        BigDecimal serviceFee,
        BigDecimal discount
) {
}
