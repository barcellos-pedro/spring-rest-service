package com.pedro.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pedro.rest.model.Employee;
import com.pedro.rest.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class EmployeeControllerTest {

    @Autowired
    private EmployeeService service;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new EmployeeController(service)).build();
    }

    @Test
    void getAll() throws Exception {
        var request = get("/employees").header("Accept", MediaTypes.HAL_JSON);

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$.content.length()").isNotEmpty());
    }

    @Test
    void getById() throws Exception {
        var request = get("/employees/1");

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Bilbo"))
                .andExpect(jsonPath("$.links").isNotEmpty())
                .andExpect(jsonPath("$.links.length()").value(2))
                .andExpect(jsonPath("$.links[0].href").value("http://localhost/employees/1"));
    }

    @Test
    void create() throws Exception {
        var employee = new Employee("test", "case", "test-role");
        var body = objectMapper.writeValueAsString(employee);

        var request = post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body);

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.name").value("test case"));
    }

    @Test
    void update() throws Exception {
        var employee = new Employee("Bilbo", "[Updated]", "burglar");
        var body = objectMapper.writeValueAsString(employee);

        var request = put("/employees/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body);

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Bilbo [Updated]"))
                .andExpect(jsonPath("$.lastName").value("[Updated]"));
    }

    @Test
    void destroy() throws Exception {
        var request = delete("/employees/{id}", 1L);
        mockMvc.perform(request)
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }
}