package io.github.williamandradesantana.restaurant.controllers;

import io.github.williamandradesantana.restaurant.dtos.AccountClosingRequest;
import io.github.williamandradesantana.restaurant.dtos.AccountClosingResponse;
import io.github.williamandradesantana.restaurant.services.AccountClosingService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders/{orderId}/closing")
public class AccountClosingController {

    private final AccountClosingService accountClosingService;

    public AccountClosingController(AccountClosingService accountClosingService) {
        this.accountClosingService = accountClosingService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountClosingResponse closeAccount(
            @PathVariable("orderId") Long orderId, @RequestBody AccountClosingRequest request
    ) {
        return accountClosingService.closeAccount(orderId, request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public AccountClosingResponse searchByOrder(@PathVariable("orderId") Long orderId) {
        return accountClosingService.searchByOrder(orderId);
    }
}
