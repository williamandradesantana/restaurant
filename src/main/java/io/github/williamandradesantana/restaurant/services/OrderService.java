package io.github.williamandradesantana.restaurant.services;

import io.github.williamandradesantana.restaurant.domain.entity.OrderEntity;
import io.github.williamandradesantana.restaurant.domain.entity.TableEntity;
import io.github.williamandradesantana.restaurant.domain.enums.OrderStatus;
import io.github.williamandradesantana.restaurant.domain.enums.TableStatus;
import io.github.williamandradesantana.restaurant.dtos.OrderRequest;
import io.github.williamandradesantana.restaurant.dtos.OrderResponse;
import io.github.williamandradesantana.restaurant.exception.BusinessException;
import io.github.williamandradesantana.restaurant.repositories.OrderRepository;
import io.github.williamandradesantana.restaurant.repositories.TableRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final TableRepository tableRepository;

    public OrderService(OrderRepository orderRepository, TableRepository tableRepository) {
        this.orderRepository = orderRepository;
        this.tableRepository = tableRepository;
    }

    public Page<OrderResponse> listOrders(Pageable pageable) {
        return orderRepository.findAll(pageable).map(OrderResponse::fromEntity);
    }

    public OrderResponse getOneOrder(Long id) {
        OrderEntity order = orderRepository.findById(id).orElseThrow(() -> new BusinessException("Order not found!"));
        return OrderResponse.fromEntity(order);
    }

    public OrderResponse createOrder(OrderRequest request) {
        TableEntity table = tableRepository.findById(request.tableId())
                .orElseThrow(() -> new BusinessException("Table not found!"));

        if (table.getStatus() != TableStatus.FREE) throw new BusinessException("The table is unavailable.");

        OrderEntity order = new OrderEntity();
        order.setTable(table);
        order.setStatus(OrderStatus.OPEN);
        order.setObservation(request.observation());

        table.setStatus(TableStatus.OCCUPIED);

        OrderEntity orderSaved = orderRepository.save(order);
        tableRepository.save(table);

        return OrderResponse.fromEntity(orderSaved);
    }
}
