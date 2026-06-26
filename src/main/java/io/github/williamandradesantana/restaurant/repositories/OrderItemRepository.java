package io.github.williamandradesantana.restaurant.repositories;

import io.github.williamandradesantana.restaurant.domain.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Long> {
    List<OrderItemEntity> findByOrder_Id(Long orderId);
}
