package com.pedro.rest.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.pedro.rest.controller.EmployeeController;
import com.pedro.rest.model.Employee;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Component
public class EmployeeModelAssembler implements RepresentationModelAssembler<Employee, EntityModel<Employee>> {

    @Override
    public EntityModel<Employee> toModel(Employee employee) {
        var selfLink = linkTo(methodOn(EmployeeController.class).getById(employee.getId())).withSelfRel();
        var rootLink = linkTo(methodOn(EmployeeController.class).getAll()).withRel("employees");
        return EntityModel.of(employee, selfLink, rootLink);
    }

    @Override
    public CollectionModel<EntityModel<Employee>> toCollectionModel(Iterable<? extends Employee> employees) {
        List<EntityModel<Employee>> entities = new ArrayList<>();
        // couldn't stream to map the employees iterable
        employees.forEach(employee -> entities.add(toModel(employee)));

        var root = linkTo(methodOn(EmployeeController.class).getAll()).withSelfRel();
        return CollectionModel.of(entities, root);
    }

    public URI getSelfURI(EntityModel<Employee> entityModel) {
        return entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri();
    }
}
