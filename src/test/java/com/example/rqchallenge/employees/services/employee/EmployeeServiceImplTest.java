package com.example.rqchallenge.employees.services.employee;

import com.example.rqchallenge.employees.services.employeeclient.EmployeeClientService;
import com.example.rqchallenge.employees.web.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class EmployeeServiceImplTest {

    @Mock
    private EmployeeClientService employeeClientService;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private static final String TEST_EMP_NAME = "FIRSTNAME LASTNAME";
    private Employee testEmployee;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testEmployee = Employee.builder()
                .id("1")
                .employeeName(TEST_EMP_NAME)
                .employeeAge("30")
                .employeeSalary("1000")
                .profileImage("Test Profile Image")
                .build();
    }

    @Test
    void findAllEmployees_success() {
        when(employeeClientService.getAllEmployees()).thenReturn(List.of(testEmployee));

        List<Employee> employees = employeeService.findAllEmployees();

        assertNotNull(employees);
        assertEquals(1, employees.size());
        assertEquals(testEmployee, employees.get(0));
    }

    @Test
    void findEmployeesByNameSearch_success() {
        when(employeeClientService.getAllEmployees()).thenReturn(List.of(testEmployee));

        List<Employee> employees = employeeService.findEmployeesByNameSearch("STNA");

        assertNotNull(employees);
        assertEquals(1, employees.size());
        assertEquals(testEmployee, employees.get(0));
    }

    @Test
    void findEmployeeById_success() {
        when(employeeClientService.getEmployeeById(eq("1"))).thenReturn(testEmployee);

        Employee employee = employeeService.findEmployeeById("1");

        assertNotNull(employee);
        assertEquals(testEmployee, employee);
    }

    @Test
    void findHighestSalaryOfEmployees_success() {
        when(employeeClientService.getAllEmployees()).thenReturn(List.of(testEmployee));

        Integer highestSalary = employeeService.findHighestSalaryOfEmployees();

        assertNotNull(highestSalary);
        assertEquals(1000, highestSalary);
    }

    @Test
    void findTopTenHighestEarningEmployeeNames_success() {
        when(employeeClientService.getAllEmployees()).thenReturn(List.of(testEmployee));

        List<String> topEarningEmployeeNames = employeeService.findTopTenHighestEarningEmployeeNames();

        assertNotNull(topEarningEmployeeNames);
        assertEquals(1, topEarningEmployeeNames.size());
        assertEquals(testEmployee.getEmployeeName(), topEarningEmployeeNames.get(0));
    }

    @Test
    void newEmployee_success() {
        when(employeeClientService.createEmployee(anyMap())).thenReturn(testEmployee);

        Employee createdEmployee = employeeService.newEmployee(testEmployee);

        assertNotNull(createdEmployee);
        assertEquals(testEmployee, createdEmployee);
    }

    @Test
    void removeEmployeeById_success() {
        when(employeeClientService.deleteEmployeeById(eq("1"))).thenReturn("Employee deleted");

        String result = employeeService.removeEmployeeById("1");

        assertNotNull(result);
        assertEquals("Employee deleted", result);
    }

    @Test
    void findAllEmployees_failure() {
        when(employeeClientService.getAllEmployees()).thenThrow(new RuntimeException("Error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
            employeeService.findAllEmployees());

        assertEquals("Error", exception.getMessage());
    }

    @Test
    void findEmployeesByNameSearch_failure() {
        when(employeeClientService.getAllEmployees()).thenThrow(new RuntimeException("Error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
            employeeService.findEmployeesByNameSearch("John"));

        assertEquals("Error", exception.getMessage());
    }

    @Test
    void findEmployeeById_failure() {
        when(employeeClientService.getEmployeeById(eq("1"))).thenThrow(new RuntimeException("Error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
            employeeService.findEmployeeById("1"));

        assertEquals("Error", exception.getMessage());
    }

    @Test
    void findHighestSalaryOfEmployees_failure() {
        when(employeeClientService.getAllEmployees()).thenThrow(new RuntimeException("Error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
            employeeService.findHighestSalaryOfEmployees());

        assertEquals("Error", exception.getMessage());
    }

    @Test
    void findTopTenHighestEarningEmployeeNames_failure() {
        when(employeeClientService.getAllEmployees()).thenThrow(new RuntimeException("Error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
            employeeService.findTopTenHighestEarningEmployeeNames());

        assertEquals("Error", exception.getMessage());
    }

    @Test
    void newEmployee_failure() {
        when(employeeClientService.createEmployee(anyMap())).thenThrow(new RuntimeException("Error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
            employeeService.newEmployee(testEmployee));

        assertEquals("Error", exception.getMessage());
    }

    @Test
    void removeEmployeeById_failure() {
        when(employeeClientService.deleteEmployeeById(eq("1"))).thenThrow(new RuntimeException("Error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
            employeeService.removeEmployeeById("1"));

        assertEquals("Error", exception.getMessage());
    }
}
