package io.github.williamandradesantana.restaurant.repositories;

import io.github.williamandradesantana.restaurant.domain.entity.OrderItemEntity;
import io.github.williamandradesantana.restaurant.domain.enums.OrderItemStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Long> {
    List<OrderItemEntity> findByOrderId(Long orderId);
    List<OrderItemEntity> findByStatusOrderByIdAsc(OrderItemStatus status);
    List<OrderItemEntity> findByOrderIdAndStatusNot(Long orderId, OrderItemStatus status);

    @Query("""
        select item
        from OrderItemEntity item
        join fetch item.product
        join fetch item.order o
        join fetch o.table
        where item.status = :status
        order by item.id
    """)
    List<OrderItemEntity> searchItemsWithProductAndOrder(OrderItemStatus status);
}
