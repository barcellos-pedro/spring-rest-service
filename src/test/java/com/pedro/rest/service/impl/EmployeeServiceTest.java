package com.pedro.rest.service.impl;

import com.pedro.rest.assembler.EmployeeModelAssembler;
import com.pedro.rest.model.Employee;
import com.pedro.rest.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @InjectMocks
    private EmployeeServiceImpl service;

    @Mock
    private EmployeeModelAssembler assembler;

    @Mock
    private EmployeeRepository repository;

    private CollectionModel<EntityModel<Employee>> entityModels;

    private final Long id = 1L;

    private final Employee employee = new Employee("Pedro", "Reis", "developer");

    private final EntityModel<Employee> entity = EntityModel.of(employee);

    @BeforeEach
    void setup() {
        employee.setId(id);
        entityModels = CollectionModel.of(List.of(entity));
    }

    @Test
    void getAll() {
        when(repository.findAll()).thenReturn(List.of(employee));
        when(service.getAll()).thenReturn(entityModels);

        var result = service.getAll();
        var size = result.getContent().size();

        assertEquals(1, size);
        verify(repository, atLeast(1)).findAll();
        verify(assembler).toCollectionModel(anyCollection());
    }

    @Test
    void getById() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(employee));
        when(service.getById(anyLong())).thenReturn(entity);

        var result = service.getById(id);
        var name = result.getContent().getName();

        assertFalse(name.isEmpty());
        verify(repository).findById(id);
        verify(assembler).toModel(employee);
    }

    @Test
    void create() {
        when(repository.save(any())).thenReturn(employee);
        when(assembler.toModel(any())).thenReturn(entity);

        service.create(employee);

        verify(repository).save(employee);
        verify(assembler, atLeast(1)).toModel(employee);
        verify(assembler).getSelfURI(entity);
    }

    @Test
    void update() {
        var expectedEmployee = new Employee("Pedro", "Barcellos", "developer");

        when(repository.findById(anyLong())).thenReturn(Optional.of(employee));
        when(repository.save(any())).thenReturn(expectedEmployee);
        when(assembler.toModel(any())).thenReturn(entity);

        service.update(expectedEmployee, id);

        assertEquals(expectedEmployee.getLastName(), employee.getLastName());
        verify(repository).save(employee);
        verify(assembler, atLeast(1)).toModel(expectedEmployee);
        verify(assembler).getSelfURI(entity);
    }

    @Test
    void delete() {
        doNothing().when(repository).deleteById(anyLong());
        service.delete(id);
        verify(repository).deleteById(id);
    }
}