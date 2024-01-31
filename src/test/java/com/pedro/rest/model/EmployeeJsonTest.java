package com.pedro.rest.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@JsonTest
public class EmployeeJsonTest {
    @Autowired
    private JacksonTester<Employee> json;

    @Autowired
    private JacksonTester<List<Employee>> jsonList;

    private final Employee emp1 = new Employee("Pedro", "Reis", "developer");

    private final Employee emp2 = new Employee("Ana", "Luiza", "developer");

    private final List<Employee> employees = List.of(emp1, emp2);

    @Test
    void shouldSerializeToEmployee() throws IOException {
        assertThat(json.write(emp1)).isEqualToJson("employee.json");
    }

    @Test
    void shouldSerializeToEmployeeList() throws IOException {
        assertThat(jsonList.write(employees)).isEqualToJson("employee-list.json");
    }

    @Test
    void shouldParseToEmployee() throws IOException {
        var jsonValue = """
                  {
                  "id": null,
                  "firstName": "Pedro",
                  "lastName": "Reis",
                  "name": "Pedro Reis",
                  "role": "developer"
                  }
                """;
        assertThat(json.parseObject(jsonValue)).isEqualTo(emp1);
    }

    @Test
    void shouldParseToEmployeeList() throws IOException {
        var jsonValue = """
                  [
                    {
                      "id": null,
                      "firstName": "Pedro",
                      "lastName": "Reis",
                      "name": "Pedro Reis",
                      "role": "developer"
                    },
                    {
                      "id": null,
                      "firstName": "Ana",
                      "lastName": "Luiza",
                      "name": "Ana Luiza",
                      "role": "developer"
                    }
                  ]
                """;
        assertThat(jsonList.parseObject(jsonValue)).isEqualTo(employees);
    }
}
