package com.pedro.rest.service.impl;

import com.pedro.rest.assembler.OrderModelAssembler;
import com.pedro.rest.controller.OrderController;
import com.pedro.rest.exception.OrderNotFoundException;
import com.pedro.rest.model.Order;
import com.pedro.rest.model.Status;
import com.pedro.rest.repository.OrderRepository;
import com.pedro.rest.service.OrderService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository repository;

    private final OrderModelAssembler assembler;

    public OrderServiceImpl(OrderRepository repository, OrderModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @Override
    public CollectionModel<EntityModel<Order>> getAll() {
        return assembler.toCollectionModel(repository.findAll());
    }

    @Override
    public EntityModel<Order> getById(Long id) {
        Order order = repository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
        return assembler.toModel(order);
    }

    @Override
    public ResponseEntity<EntityModel<Order>> create(Order order) {
        order.setStatus(Status.IN_PROGRESS);
        Order newOrder = repository.save(order);
        URI selfLink = linkTo(methodOn(OrderController.class).getById(newOrder.getId())).toUri();
        return ResponseEntity.created(selfLink).body(assembler.toModel(newOrder));
    }

    @Override
    public ResponseEntity<?> complete(Long id) {
        Order order = repository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));

        if (order.getStatus() == Status.IN_PROGRESS) {
            order.setStatus(Status.COMPLETED);
            return ResponseEntity.ok(assembler.toModel(repository.save(order)));
        }

        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                .body(createProblem(order));
    }

    @Override
    public ResponseEntity<?> cancel(Long id) {
        Order order = repository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));

        if (order.getStatus() == Status.IN_PROGRESS) {
            order.setStatus(Status.CANCELLED);
            return ResponseEntity.ok(assembler.toModel(repository.save(order)));
        }

        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                .body(createProblem(order));
    }

    private static Problem createProblem(Order order) {
        var detail = String.format("You can't cancel an order that is in the [%s] status", order.getStatus().getDisplayName());

        return Problem.create()
                .withTitle("Method not allowed")
                .withDetail(detail);
    }
}
