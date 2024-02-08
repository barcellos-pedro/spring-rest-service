package com.pedro.rest.controller;

import com.pedro.rest.model.Employee;
import com.pedro.rest.service.EmployeeService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService service;

    EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping
    public CollectionModel<EntityModel<Employee>> getAll() {
        return service.getAll();
    }

    @GetMapping("/role")
    public CollectionModel<EntityModel<Employee>> getAllByRole(@RequestParam(name = "search", defaultValue = "") String role) {
        return service.getAllByRole(role);
    }

    @GetMapping("/{id}")
    public EntityModel<Employee> getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Employee employee) {
        return service.create(employee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Employee newEmployee, @PathVariable Long id) {
        return service.update(newEmployee, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return service.delete(id);
    }
}
