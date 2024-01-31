package com.pedro.rest.service;

import com.pedro.rest.model.Order;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

public interface OrderService {
    CollectionModel<EntityModel<Order>> getAll();

    EntityModel<Order> getById(Long id);

    ResponseEntity<EntityModel<Order>> create(Order order);

    ResponseEntity<?> complete(Long id);

    ResponseEntity<?> cancel(Long id);
}
