package com.pedro.rest.service;

import com.pedro.rest.model.Employee;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

public interface EmployeeService {
    CollectionModel<EntityModel<Employee>> getAll();

    EntityModel<Employee> getById(Long id);

    ResponseEntity<?> create(Employee employee);

    ResponseEntity<?> update(Employee employee, Long id);

    ResponseEntity<?> delete(Long id);
}
