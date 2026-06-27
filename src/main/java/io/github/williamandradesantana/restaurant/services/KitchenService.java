package io.github.williamandradesantana.restaurant.services;

import io.github.williamandradesantana.restaurant.domain.entity.OrderItemEntity;
import io.github.williamandradesantana.restaurant.domain.enums.OrderItemStatus;
import io.github.williamandradesantana.restaurant.dtos.KitchenItemResponse;
import io.github.williamandradesantana.restaurant.exception.BusinessException;
import io.github.williamandradesantana.restaurant.repositories.OrderItemRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class KitchenService {

    private final OrderItemRepository orderItemRepository;

    public KitchenService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    public List<KitchenItemResponse> listPendingItems() {
        return orderItemRepository.findByStatusOrderByIdAsc(OrderItemStatus.PENDING)
                .stream()
                .map(KitchenItemResponse::fromEntity).toList();
    }

    public List<KitchenItemResponse> listInPreparationItems() {
        return orderItemRepository.findByStatusOrderByIdAsc(OrderItemStatus.IN_PREPARATION)
                .stream()
                .map(KitchenItemResponse::fromEntity).toList();
    }

    public KitchenItemResponse beginPreparation(Long itemId) {
        OrderItemEntity orderItem = searchItemById(itemId);

        if (orderItem.getStatus() != OrderItemStatus.PENDING)
            throw new BusinessException("Only pending items can begin preparation.");

        orderItem.setStatus(OrderItemStatus.IN_PREPARATION);
        orderItem.setPreparationStartDate(LocalDateTime.now());
        return KitchenItemResponse.fromEntity(orderItemRepository.save(orderItem));
    }

    public KitchenItemResponse markAsReady(Long itemId) {
        OrderItemEntity orderItem = searchItemById(itemId);

        if (orderItem.getStatus() != OrderItemStatus.IN_PREPARATION)
            throw new BusinessException("Only items currently being prepared can be ready.");

        orderItem.setStatus(OrderItemStatus.READY);
        orderItem.setCompletionDate(LocalDateTime.now());
        return KitchenItemResponse.fromEntity(orderItemRepository.save(orderItem));
    }

    public KitchenItemResponse sendItem(Long itemId) {
        OrderItemEntity orderItem = searchItemById(itemId);

        if (orderItem.getStatus() != OrderItemStatus.READY)
            throw new BusinessException("Only ready items can be delivered.");

        orderItem.setStatus(OrderItemStatus.DELIVERED);
        orderItem.setDeliveryDate(LocalDateTime.now());
        return KitchenItemResponse.fromEntity(orderItemRepository.save(orderItem));
    }

    private OrderItemEntity searchItemById(Long itemId) {
        return orderItemRepository.findById(itemId).orElseThrow(() -> new BusinessException("Item not found!"));
    }
}
