package io.github.williamandradesantana.restaurant.dtos;

import io.github.williamandradesantana.restaurant.domain.entity.OrderEntity;
import io.github.williamandradesantana.restaurant.domain.entity.TableEntity;

public record OrderRequest(
        Long tableId,
        String observation
) {
}
