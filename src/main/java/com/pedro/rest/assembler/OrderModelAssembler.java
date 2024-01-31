package com.pedro.rest.assembler;

import com.pedro.rest.controller.OrderController;
import com.pedro.rest.model.Order;
import com.pedro.rest.model.Status;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class OrderModelAssembler implements RepresentationModelAssembler<Order, EntityModel<Order>> {
    @Override
    public EntityModel<Order> toModel(Order order) {
        EntityModel<Order> orderModel = EntityModel.of(order);

        // Unconditional links to single-item resource and aggregate root
        var selfLink = linkTo(methodOn(OrderController.class).getOrderById(order.getId())).withSelfRel();
        var rootLink = linkTo(methodOn(OrderController.class).getAllOrders()).withRel("orders");
        orderModel.add(selfLink, rootLink);

        // Conditional links based on state of the order
        if (order.getStatus() == Status.IN_PROGRESS) {
            var cancelLink = linkTo(methodOn(OrderController.class).cancel(order.getId())).withRel("cancel");
            var completeLink = linkTo(methodOn(OrderController.class).complete(order.getId())).withRel("complete");
            orderModel.add(cancelLink, completeLink);
        }

        return orderModel;
    }

    @Override
    public CollectionModel<EntityModel<Order>> toCollectionModel(Iterable<? extends Order> orders) {
        List<EntityModel<Order>> entities = new ArrayList<>();
        // couldn't stream to map the orders iterable
        orders.forEach(order -> entities.add(toModel(order)));

        var root = linkTo(methodOn(OrderController.class).getAllOrders()).withSelfRel();
        return CollectionModel.of(entities, root);
    }
}
