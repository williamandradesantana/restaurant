package io.github.williamandradesantana.restaurant.controllers;

import io.github.williamandradesantana.restaurant.dtos.OrderItemRequest;
import io.github.williamandradesantana.restaurant.dtos.OrderItemResponse;
import io.github.williamandradesantana.restaurant.dtos.OrderRequest;
import io.github.williamandradesantana.restaurant.dtos.OrderResponse;
import io.github.williamandradesantana.restaurant.services.OrderService;
import io.github.williamandradesantana.restaurant.services.PaymentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/orders")
public class OrderController {

    private final OrderService orderService;
    private final PaymentService paymentService;

    public OrderController(OrderService orderService, PaymentService paymentService) {
        this.orderService = orderService;
        this.paymentService = paymentService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<OrderResponse> listOrders(Pageable pageable) {
        return orderService.listOrders(pageable);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderResponse getOneOrder(@PathVariable("id") Long id) {
        return orderService.getOneOrder(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse getOneOrder(@RequestBody OrderRequest request) {
        return orderService.createOrder(request);
    }

    @GetMapping("/{orderId}/items")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderItemResponse> listItems(@PathVariable("orderId") Long orderId) {
        return orderService.listOrderItems(orderId);
    }

    @PostMapping("/{orderId}/items")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderItemResponse addItem(
            @PathVariable("orderId") Long orderId, @RequestBody OrderItemRequest request
    ) {
        return orderService.addOrderItem(orderId, request);
    }

    @PostMapping("/{orderId}/pay")
    @ResponseStatus(HttpStatus.CREATED)
    public void pay(@PathVariable("orderId") Long orderId, @RequestParam String paymentMethod) {
        paymentService.pay(orderId, paymentMethod);
    }
}
