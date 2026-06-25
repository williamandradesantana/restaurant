package io.github.williamandradesantana.restaurant.repositories;

import io.github.williamandradesantana.restaurant.domain.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}
