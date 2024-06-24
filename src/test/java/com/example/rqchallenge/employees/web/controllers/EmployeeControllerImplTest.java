package com.example.rqchallenge.employees.web.controllers;

import com.example.rqchallenge.employees.services.employee.EmployeeService;
import com.example.rqchallenge.employees.web.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    private static final String TEST_EMP_NAME = "Test Employee";
    private Employee testEmployee;

    @BeforeEach
    void setUp() {
        testEmployee = Employee.builder()
                .id("1")
                .employeeName(TEST_EMP_NAME)
                .employeeSalary("100000")
                .employeeAge("30")
                .profileImage("")
                .build();
    }

    @Test
    void getAllEmployees_success() throws Exception {
        List<Employee> employees = Collections.singletonList(testEmployee);

        when(employeeService.findAllEmployees()).thenReturn(employees);

        mockMvc.perform(get("/api/v1/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].employee_name").value(TEST_EMP_NAME)); // updated JSON path
    }


    @Test
    void getEmployeesByNameSearch_success() throws Exception {
        List<Employee> employees = Collections.singletonList(testEmployee);

        when(employeeService.findEmployeesByNameSearch(anyString())).thenReturn(employees);

        mockMvc.perform(get("/api/v1/employees/search")
                        .param("searchString", "John"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].employee_name").value(TEST_EMP_NAME));
    }

    @Test
    void getEmployeeById_success() throws Exception {
        when(employeeService.findEmployeeById(anyString())).thenReturn(testEmployee);

        mockMvc.perform(get("/api/v1/employees/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.employee_name").value(TEST_EMP_NAME));
    }

    @Test
    void getHighestSalaryOfEmployees_success() throws Exception {
        when(employeeService.findHighestSalaryOfEmployees()).thenReturn(5000);

        mockMvc.perform(get("/api/v1/employees/highestSalary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(5000));
    }

    @Test
    void getTopTenHighestEarningEmployeeNames_success() throws Exception {
        List<String> employeeNames = Collections.singletonList(TEST_EMP_NAME);

        when(employeeService.findTopTenHighestEarningEmployeeNames()).thenReturn(employeeNames);

        mockMvc.perform(get("/api/v1/employees/topTenHighestEarning"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value(TEST_EMP_NAME));
    }

    @Test
    void createEmployee_success() throws Exception {
        when(employeeService.newEmployee(any(Employee.class))).thenReturn(testEmployee);

        mockMvc.perform(post("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"John Doe\",\"salary\":\"5000\",\"age\":\"30\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.employee_name").value(TEST_EMP_NAME));
    }

    @Test
    void deleteEmployeeById_success() throws Exception {
        when(employeeService.removeEmployeeById(anyString())).thenReturn("successfully! deleted Record");

        mockMvc.perform(delete("/api/v1/employees/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("successfully! deleted Record"));
    }

    @Test
    void getAllEmployees_failure() throws Exception {
        when(employeeService.findAllEmployees()).thenThrow(new RuntimeException("Error fetching all employees"));

        mockMvc.perform(get("/api/v1/employees"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("An error occurred. Please try again later."));
    }

    @Test
    void getEmployeeById_failure() throws Exception {
        when(employeeService.findEmployeeById(anyString())).thenThrow(new RuntimeException("Error fetching employee by id"));

        mockMvc.perform(get("/api/v1/employees/{id}", "1"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("An error occurred. Please try again later."));
    }

    @Test
    void getEmployeesByNameSearch_failure() throws Exception {
        when(employeeService.findEmployeesByNameSearch(anyString())).thenThrow(new RuntimeException("Error searching employees by name"));

        mockMvc.perform(get("/api/v1/employees/search").param("searchString", "John"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("An error occurred. Please try again later."));
    }
}
