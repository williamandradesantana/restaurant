package io.github.williamandradesantana.restaurant.services;

import io.github.williamandradesantana.restaurant.domain.entity.OrderEntity;
import io.github.williamandradesantana.restaurant.domain.entity.OrderItemEntity;
import io.github.williamandradesantana.restaurant.domain.entity.ProductEntity;
import io.github.williamandradesantana.restaurant.domain.entity.TableEntity;
import io.github.williamandradesantana.restaurant.domain.enums.OrderItemStatus;
import io.github.williamandradesantana.restaurant.domain.enums.OrderStatus;
import io.github.williamandradesantana.restaurant.domain.enums.TableStatus;
import io.github.williamandradesantana.restaurant.dtos.OrderItemRequest;
import io.github.williamandradesantana.restaurant.dtos.OrderItemResponse;
import io.github.williamandradesantana.restaurant.dtos.OrderRequest;
import io.github.williamandradesantana.restaurant.dtos.OrderResponse;
import io.github.williamandradesantana.restaurant.exception.BusinessException;
import io.github.williamandradesantana.restaurant.repositories.OrderItemRepository;
import io.github.williamandradesantana.restaurant.repositories.OrderRepository;
import io.github.williamandradesantana.restaurant.repositories.ProductRepository;
import io.github.williamandradesantana.restaurant.repositories.TableRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final TableRepository tableRepository;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderService(OrderRepository orderRepository, TableRepository tableRepository, ProductRepository productRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.tableRepository = tableRepository;
        this.productRepository = productRepository;
        this.orderItemRepository = orderItemRepository;
    }

    public Page<OrderResponse> listOrders(Pageable pageable) {
        return orderRepository.findAll(pageable).map(OrderResponse::fromEntity);
    }

    public OrderResponse getOneOrder(Long id) {
        OrderEntity order = searchOrderById(id);
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

    public OrderItemResponse addOrderItem(Long orderId, OrderItemRequest request) {
        OrderEntity order = searchOrderById(orderId);

        if (order.getStatus() != OrderStatus.OPEN)
            throw new BusinessException("Items can only be added to open orders.");

        ProductEntity product = productRepository.findById(request.productId())
                .orElseThrow(() -> new BusinessException("Product not found!"));

        if (!product.getAvailable()) throw new BusinessException("Product unavailable!");
        if (request.quantity() == null || request.quantity() <= 0)
            throw new BusinessException("The quantity must be greater than zero");

        OrderItemEntity orderItem = new OrderItemEntity();
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setQuantity(request.quantity());
        orderItem.setUnitPrice(product.getPrice());
        orderItem.setObservation(request.observation());
        orderItem.setStatus(OrderItemStatus.PENDING);

        OrderItemEntity orderItemSaved = orderItemRepository.save(orderItem);
        return OrderItemResponse.fromEntity(orderItemSaved);
    }

    public List<OrderItemResponse> listOrderItems(Long orderId) {
        searchOrderById(orderId);
        return orderItemRepository.findByOrderId(orderId)
                .stream().map(OrderItemResponse::fromEntity).collect(Collectors.toList());
    }

    private OrderEntity searchOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new BusinessException("Order not found!"));
    }
}
