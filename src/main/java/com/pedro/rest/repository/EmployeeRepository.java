/*
    JPA Query Methods
    https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html
* */

package com.pedro.rest.repository;

import com.pedro.rest.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByRoleIgnoreCaseContaining(String role);
}
