package io.github.williamandradesantana.restaurant.controllers;

import io.github.williamandradesantana.restaurant.dtos.KitchenItemResponse;
import io.github.williamandradesantana.restaurant.services.KitchenService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kitchen")
public class KitchenController {

    private final KitchenService kitchenService;

    public KitchenController(KitchenService kitchenService) {
        this.kitchenService = kitchenService;
    }

    @GetMapping("/pending-items")
    @ResponseStatus(HttpStatus.OK)
    public List<KitchenItemResponse> listPendingItems() {
        return kitchenService.listPendingItems();
    }

    @GetMapping("/items-in-preparation")
    @ResponseStatus(HttpStatus.OK)
    public List<KitchenItemResponse> listInPreparationItems() {
        return kitchenService.listInPreparationItems();
    }

    @PatchMapping("/items/{itemId}/begin-preparation")
    @ResponseStatus(HttpStatus.OK)
    public KitchenItemResponse beginPreparation(@PathVariable("itemId") Long itemId) {
        return kitchenService.beginPreparation(itemId);
    }

    @PatchMapping("/items/{itemId}/mark-as-ready")
    @ResponseStatus(HttpStatus.OK)
    public KitchenItemResponse markAsReady(@PathVariable("itemId") Long itemId) {
        return kitchenService.markAsReady(itemId);
    }

    @PatchMapping("/items/{itemId}/send-item")
    @ResponseStatus(HttpStatus.OK)
    public KitchenItemResponse sendItem(@PathVariable("itemId") Long itemId) {
        return kitchenService.sendItem(itemId);
    }
}
