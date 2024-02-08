package com.pedro.rest.service.impl;

import com.pedro.rest.assembler.EmployeeModelAssembler;
import com.pedro.rest.exception.EmployeeNotFoundException;
import com.pedro.rest.model.Employee;
import com.pedro.rest.repository.EmployeeRepository;
import com.pedro.rest.service.EmployeeService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;

    private final EmployeeModelAssembler assembler;

    public EmployeeServiceImpl(EmployeeRepository repository, EmployeeModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @Override
    public CollectionModel<EntityModel<Employee>> getAll() {
        return assembler.toCollectionModel(repository.findAll());
    }

    @Override
    public CollectionModel<EntityModel<Employee>> getAllByRole(String role) {
        return assembler.toCollectionModel(repository.findByRoleIgnoreCaseContaining(role));
    }

    @Override
    public EntityModel<Employee> getById(Long id) {
        var employee = repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
        return assembler.toModel(employee);
    }

    @Override
    public ResponseEntity<?> create(Employee employee) {
        EntityModel<Employee> entityModel = assembler.toModel(repository.save(employee));
        return ResponseEntity.created(assembler.getSelfURI(entityModel)).body(entityModel);
    }

    @Override
    public ResponseEntity<?> update(Employee newEmployee, Long id) {
        Employee updatedEmployee = repository.findById(id)
                .map(employee -> {
                    employee.setName(newEmployee.getName());
                    employee.setRole(newEmployee.getRole());
                    return repository.save(employee);
                }).orElseGet(() -> repository.save(newEmployee));

        EntityModel<Employee> entityModel = assembler.toModel(updatedEmployee);
        return ResponseEntity.created(assembler.getSelfURI(entityModel)).body(entityModel);
    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
