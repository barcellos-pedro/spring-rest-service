package com.pedro.rest.configuration;

import com.pedro.rest.model.Order;
import com.pedro.rest.model.Status;
import com.pedro.rest.repository.EmployeeRepository;
import com.pedro.rest.model.Employee;
import com.pedro.rest.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    private static final List<Employee> EMPLOYEES = List.of(
            new Employee("Bilbo", "Baggins", "burglar"),
            new Employee("Frodo", "Baggins", "thief")
    );

    private static final List<Order> ORDERS = List.of(
            new Order("Macbook Pro", Status.COMPLETED),
            new Order("iPhone", Status.IN_PROGRESS)
    );

    @Bean
    CommandLineRunner initDatabase(EmployeeRepository employeeRepository, OrderRepository orderRepository) {
        return args -> {
            EMPLOYEES.forEach(employeeRepository::save);
            ORDERS.forEach(orderRepository::save);

            employeeRepository.findAll().forEach(employee -> log.info("Preloaded " + employee));
            orderRepository.findAll().forEach(order -> log.info("Preloaded " + order));
        };
    }
}
