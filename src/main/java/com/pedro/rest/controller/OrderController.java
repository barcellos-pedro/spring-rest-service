package com.pedro.rest.controller;

import com.pedro.rest.model.Order;
import com.pedro.rest.service.OrderService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @GetMapping
    public CollectionModel<EntityModel<Order>> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public EntityModel<Order> getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public ResponseEntity<EntityModel<Order>> create(@RequestBody Order order) {
        return service.create(order);
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<?> complete(@PathVariable Long id) {
        return service.complete(id);
    }

    @DeleteMapping("/{id}/cancel")
    public ResponseEntity<?> cancel(@PathVariable Long id) {
        return service.cancel(id);
    }
}
