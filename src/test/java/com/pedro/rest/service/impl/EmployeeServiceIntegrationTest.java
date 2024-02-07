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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ActiveProfiles("test")
public class EmployeeServiceIntegrationTest {
    @Autowired
    private EmployeeServiceImpl service;

    @MockBean
    private EmployeeModelAssembler assembler;

    @MockBean
    private EmployeeRepository repository;

    private CollectionModel<EntityModel<Employee>> entityModels;

    @BeforeEach
    void setup() {
        Employee employee = new Employee("Pedro", "Reis", "developer");
        EntityModel<Employee> entity = EntityModel.of(employee);
        entityModels = CollectionModel.of(List.of(entity));
    }

    @Test
    void getAll() {
        when(service.getAll()).thenReturn(entityModels);

        var result = service.getAll();
        var size = result.getContent().size();

        assertEquals(1, size);
        verify(repository, atLeast(1)).findAll();
        verify(assembler).toCollectionModel(anyCollection());
    }
}
