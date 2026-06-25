package io.github.williamandradesantana.restaurant.controllers;

import io.github.williamandradesantana.restaurant.dtos.OrderRequest;
import io.github.williamandradesantana.restaurant.dtos.OrderResponse;
import io.github.williamandradesantana.restaurant.services.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
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

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse getOneOrder(@RequestBody OrderRequest request) {
        return orderService.createOrder(request);
    }
}
