package io.github.williamandradesantana.restaurant.services;

import io.github.williamandradesantana.restaurant.client.PaymentClient;
import io.github.williamandradesantana.restaurant.domain.entity.AccountClosingEntity;
import io.github.williamandradesantana.restaurant.domain.entity.OrderEntity;
import io.github.williamandradesantana.restaurant.domain.entity.PaymentEntity;
import io.github.williamandradesantana.restaurant.domain.entity.TableEntity;
import io.github.williamandradesantana.restaurant.domain.enums.OrderStatus;
import io.github.williamandradesantana.restaurant.domain.enums.PaymentMethod;
import io.github.williamandradesantana.restaurant.domain.enums.PaymentStatus;
import io.github.williamandradesantana.restaurant.domain.enums.TableStatus;
import io.github.williamandradesantana.restaurant.dtos.PaymentRequest;
import io.github.williamandradesantana.restaurant.dtos.PaymentResponse;
import io.github.williamandradesantana.restaurant.exception.BusinessException;
import io.github.williamandradesantana.restaurant.repositories.AccountClosingRepository;
import io.github.williamandradesantana.restaurant.repositories.OrderRepository;
import io.github.williamandradesantana.restaurant.repositories.PaymentRepository;
import io.github.williamandradesantana.restaurant.repositories.TableRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentService {

    private final PaymentClient paymentClient;
    private final AccountClosingRepository accountClosingRepository;
    private final TableRepository tableRepository;
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

    public PaymentService(
        PaymentClient paymentClient,
        AccountClosingRepository accountClosingRepository,
        TableRepository tableRepository,
        OrderRepository orderRepository,
        PaymentRepository paymentRepository
    ) {
        this.paymentClient = paymentClient;
        this.accountClosingRepository = accountClosingRepository;
        this.tableRepository = tableRepository;
        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
    }

    @Transactional
    public void pay(Long orderId, String paymentMethod) {
        AccountClosingEntity accountClosing = accountClosingRepository.findByOrderId(orderId)
                .orElseThrow(() -> new BusinessException("Order not found!"));

        PaymentResponse response = paymentClient.processPayment(
                new PaymentRequest(accountClosing.getTotal(), paymentMethod)
        );

        if ("APPROVED".equals(response.status())) {
            OrderEntity order = accountClosing.getOrder();
            order.setStatus(OrderStatus.CLOSED);

            TableEntity table = order.getTable();
            table.setStatus(TableStatus.FREE);

            PaymentEntity payment = new PaymentEntity();
            payment.setOrder(order);
            payment.setPaymentMethod(PaymentMethod.valueOf(paymentMethod));
            payment.setStatus(PaymentStatus.APPROVED);
            payment.setValue(accountClosing.getTotal());
            payment.setPaymentDate(accountClosing.getClosingDate());

            orderRepository.save(order);
            tableRepository.save(table);
            paymentRepository.save(payment);

        }
    }
}
