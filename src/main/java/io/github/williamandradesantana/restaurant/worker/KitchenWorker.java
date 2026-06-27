package io.github.williamandradesantana.restaurant.worker;

import io.github.williamandradesantana.restaurant.domain.entity.OrderItemEntity;
import io.github.williamandradesantana.restaurant.domain.enums.OrderItemStatus;
import io.github.williamandradesantana.restaurant.repositories.OrderItemRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class KitchenWorker {

    private final OrderItemRepository orderItemRepository;
    private final ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();

    public KitchenWorker(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Scheduled(fixedRate = 60000)
    public void checkOverdueItems() {
        List<OrderItemEntity> itemsBeginPrepared = orderItemRepository
                .searchItemsWithProductAndOrder(OrderItemStatus.IN_PREPARATION);

        for (OrderItemEntity item : itemsBeginPrepared) {
            executorService.submit(() -> verifyItem(item));
        }
    }

    private void verifyItem(OrderItemEntity item) {
        if (item.getPreparationStartDate() == null) return;

        Integer preparationTime = item.getProduct().getPreparationTimeInMinutes();
        if (preparationTime == null || preparationTime <= 0) return;

        long preparationTimeInMinutes = Duration
                .between(item.getPreparationStartDate(), LocalDateTime.now()).toMinutes();

        if (preparationTimeInMinutes > preparationTime) {
            System.out.println("""
                    [Kitchen alert] - Overdue item
                    Order: %d
                    Table: %d
                    Product: %s
                    Waiting time: %d minutes
                    Preparation time: %d minutes
                    """
                    .formatted(
                        item.getOrder().getId(),
                        item.getOrder().getTable().getNumber(),
                        item.getProduct().getName(),
                        preparationTimeInMinutes,
                        preparationTime
            ));
        }
    }
}
