package io.github.williamandradesantana.restaurant.repositories;

import io.github.williamandradesantana.restaurant.domain.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Long> {
}
