package io.github.williamandradesantana.restaurant.services;

import io.github.williamandradesantana.restaurant.domain.entity.AccountClosingEntity;
import io.github.williamandradesantana.restaurant.domain.entity.OrderEntity;
import io.github.williamandradesantana.restaurant.domain.entity.OrderItemEntity;
import io.github.williamandradesantana.restaurant.domain.enums.OrderItemStatus;
import io.github.williamandradesantana.restaurant.domain.enums.OrderStatus;
import io.github.williamandradesantana.restaurant.dtos.AccountClosingRequest;
import io.github.williamandradesantana.restaurant.dtos.AccountClosingResponse;
import io.github.williamandradesantana.restaurant.exception.BusinessException;
import io.github.williamandradesantana.restaurant.repositories.AccountClosingRepository;
import io.github.williamandradesantana.restaurant.repositories.OrderItemRepository;
import io.github.williamandradesantana.restaurant.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AccountClosingService {

    private final AccountClosingRepository accountClosingRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public AccountClosingService(AccountClosingRepository accountClosingRepository, OrderRepository orderRepository, OrderItemRepository orderItemRepository) {
        this.accountClosingRepository = accountClosingRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    public AccountClosingResponse closeAccount(Long orderId, AccountClosingRequest request) {
        OrderEntity order = searchOrderById(orderId);

        if (order.getStatus() == OrderStatus.CLOSED) throw new BusinessException("The order is already closed.");
        if (order.getStatus() == OrderStatus.CANCELED) throw new BusinessException("The cancelled order cannot be closed.");
        if (accountClosingRepository.existsByOrderId(orderId)) throw new BusinessException("This order is already closed.");

        List<OrderItemEntity> items = orderItemRepository.findByOrderId(orderId);
        if (items.isEmpty()) throw new BusinessException("It is not possible to close an account that has no items.");

        List<OrderItemEntity> undeliveredItems =
                orderItemRepository.findByOrderIdAndStatusNot(orderId, OrderItemStatus.DELIVERED);

        if (!undeliveredItems.isEmpty()) throw new BusinessException("All items must be delivered to close the account.");

        BigDecimal subtotal = items.stream()
                .map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal serviceFee = request.serviceFee() != null ? request.serviceFee() : BigDecimal.ZERO;
        BigDecimal discount = request.discount() != null ? request.discount() : BigDecimal.ZERO;

        if (serviceFee.compareTo(BigDecimal.ZERO) < 0) throw new BusinessException("The service fee cannot be negative.");
        if (discount.compareTo(BigDecimal.ZERO) < 0) throw new BusinessException("The discount cannot be negative.");

        BigDecimal total = subtotal.add(serviceFee.subtract(discount));
        if (total.compareTo(BigDecimal.ZERO) < 0) throw new BusinessException("The total fee cannot be negative.");

        AccountClosingEntity accountClosing = new AccountClosingEntity();
        accountClosing.setOrder(order);
        accountClosing.setSubtotal(subtotal);
        accountClosing.setServiceFee(serviceFee);
        accountClosing.setDiscount(discount);
        accountClosing.setTotal(total);

        order.setStatus(OrderStatus.CLOSED);
        order.setClosingDate(LocalDateTime.now());

        AccountClosingEntity accountClosingSaved = accountClosingRepository.save(accountClosing);
        orderRepository.save(order);

        return AccountClosingResponse.fromEntity(accountClosingSaved);
    }

    public AccountClosingResponse searchByOrder(Long orderId) {
        AccountClosingEntity accountClosing = accountClosingRepository.findByOrderId(orderId)
                .orElseThrow(() -> new BusinessException("Account closing not found!"));
        return AccountClosingResponse.fromEntity(accountClosing);
    }

    private OrderEntity searchOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new BusinessException("Order not found!"));
    }
}
