package io.github.williamandradesantana.restaurant.dtos;

public record PaymentResponse(
        String status,
        String transactionalCode
) {
}
