package com.example.rqchallenge.employees.services.employeeclient;

import com.example.rqchallenge.employees.exceptions.InvalidClientResponseException;
import com.example.rqchallenge.employees.web.model.Employee;
import com.example.rqchallenge.employees.web.model.client.CreateEmployeeResponse;
import com.example.rqchallenge.employees.web.model.client.DeleteEmployeeResponse;
import com.example.rqchallenge.employees.web.model.client.EmployeeResponse;
import com.example.rqchallenge.employees.web.model.client.EmployeesResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.validation.Validator;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class EmployeeClientServiceImplTest {

    @Mock
    private RestTemplateBuilder restTemplateBuilder;

    @Mock
    private Validator validator;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private EmployeeClientServiceImpl employeeClientService;

    private static final String TEST_EMP_NAME = "TEST_EMPLOYEE";
    private Employee testEmployee;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(restTemplateBuilder.build()).thenReturn(restTemplate);
        employeeClientService.init();

        testEmployee = Employee.builder()
                .id("1")
                .employeeName(TEST_EMP_NAME)
                .employeeAge("30")
                .employeeSalary("1000")
                .profileImage("Test Profile Image")
                .build();
    }

    @Test
    void getAllEmployees_success() throws InvalidClientResponseException {
        EmployeesResponse mockResponse = new EmployeesResponse();
        mockResponse.setStatus("success");
        mockResponse.setData(List.of(testEmployee));

        when(restTemplate.getForObject(eq("/api/v1/employees"), eq(EmployeesResponse.class))).thenReturn(mockResponse);

        List<Employee> employees = employeeClientService.getAllEmployees();

        assertNotNull(employees);
        assertEquals(1, employees.size());
        assertEquals(TEST_EMP_NAME, employees.get(0).getEmployeeName());
    }

    @Test
    void getAllEmployees_invalidResponse() {
        EmployeesResponse mockResponse = new EmployeesResponse();
        mockResponse.setStatus("error");

        when(restTemplate.getForObject(eq("/api/v1/employees"), eq(EmployeesResponse.class))).thenReturn(mockResponse);

        assertThrows(InvalidClientResponseException.class, () -> {
            employeeClientService.getAllEmployees();
        });
    }

    @Test
    void getEmployeeById_success() throws InvalidClientResponseException {
        EmployeeResponse mockResponse = new EmployeeResponse();
        mockResponse.setStatus("success");
        mockResponse.setData(testEmployee);

        when(restTemplate.getForObject(eq("/api/v1/employee/{id}"), eq(EmployeeResponse.class), eq("1"))).thenReturn(mockResponse);

        Employee employee = employeeClientService.getEmployeeById("1");

        assertNotNull(employee);
        assertEquals(TEST_EMP_NAME, employee.getEmployeeName());
    }

    @Test
    void getEmployeeById_invalidResponse() {
        EmployeeResponse mockResponse = new EmployeeResponse();
        mockResponse.setStatus("error");

        when(restTemplate.getForObject(eq("/api/v1/employee/{id}"), eq(EmployeeResponse.class), eq("1"))).thenReturn(mockResponse);

        assertThrows(InvalidClientResponseException.class, () -> {
            employeeClientService.getEmployeeById("1");
        });
    }

    @Test
    void createEmployee_success() throws InvalidClientResponseException {
        CreateEmployeeResponse mockResponse = new CreateEmployeeResponse();
        mockResponse.setStatus("success");
        mockResponse.setData(testEmployee);

        Map<String, Object> employeeMap = new HashMap<>();
        employeeMap.put("name", TEST_EMP_NAME);
        employeeMap.put("salary", "1000");
        employeeMap.put("age", "30");

        when(restTemplate.postForObject(eq("/api/v1/create"), eq(employeeMap), eq(CreateEmployeeResponse.class))).thenReturn(mockResponse);

        Employee employee = employeeClientService.createEmployee(employeeMap);

        assertNotNull(employee);
        assertEquals(TEST_EMP_NAME, employee.getEmployeeName());
    }

    @Test
    void createEmployee_invalidResponse() {
        CreateEmployeeResponse mockResponse = new CreateEmployeeResponse();
        mockResponse.setStatus("error");

        Map<String, Object> employeeMap = new HashMap<>();
        employeeMap.put("name", TEST_EMP_NAME);
        employeeMap.put("salary", "1000");
        employeeMap.put("age", "30");

        when(restTemplate.postForObject(eq("/api/v1/create"), eq(employeeMap), eq(CreateEmployeeResponse.class))).thenReturn(mockResponse);

        assertThrows(InvalidClientResponseException.class, () -> {
            employeeClientService.createEmployee(employeeMap);
        });
    }

    @Test
    void deleteEmployeeById_success() throws InvalidClientResponseException {
        DeleteEmployeeResponse mockResponse = new DeleteEmployeeResponse();
        mockResponse.setStatus("success");
        mockResponse.setMessage("successfully! deleted Record");

        ResponseEntity<DeleteEmployeeResponse> responseEntity = ResponseEntity.ok(mockResponse);

        when(restTemplate.exchange(eq("/api/v1/delete/{id}"), eq(HttpMethod.DELETE), eq(null), eq(DeleteEmployeeResponse.class), eq("1")))
                .thenReturn(responseEntity);
        String message = employeeClientService.deleteEmployeeById("1");

        assertNotNull(message);
        assertEquals("successfully! deleted Record", message);
    }

    @Test
    void deleteEmployeeById_invalidResponse() {
        DeleteEmployeeResponse mockResponse = new DeleteEmployeeResponse();
        mockResponse.setStatus("error");

        ResponseEntity<DeleteEmployeeResponse> responseEntity = ResponseEntity.ok(mockResponse);

        when(restTemplate.exchange(eq("/api/v1/delete/{id}"), eq(HttpMethod.DELETE), eq(null), eq(DeleteEmployeeResponse.class), eq("1")))
                .thenReturn(responseEntity);

        assertThrows(InvalidClientResponseException.class, () -> {
            employeeClientService.deleteEmployeeById("1");
        });
    }
}
