package com.example.rqchallenge.employees.web.controllers;

import com.example.rqchallenge.employees.services.employee.EmployeeService;
import com.example.rqchallenge.employees.web.model.Employee;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeControllerImpl implements IEmployeeController {

    private final EmployeeService employeeService;

    @Override
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() throws IOException {
        log.info("Get all employees");
        try {
            List<Employee> employees = employeeService.findAllEmployees();
            log.info("Successfully retrieved all employees");
            return ResponseEntity.ok(employees);
        } catch (Exception e) {
            log.error("Error fetching all employees", e);
            throw new IOException("Error fetching all employees", e);
        }
    }

    @Override
    @GetMapping("/search")
    public ResponseEntity<List<Employee>> getEmployeesByNameSearch(@RequestParam String searchString) {
        log.info("Get employees by name search {}", searchString);
        List<Employee> employees = employeeService.findEmployeesByNameSearch(searchString);
        log.info("Successfully retrieved matching employees");
        return ResponseEntity.ok(employees);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable String id) {
        log.info("Get employee by id {}", id);
        Employee employee = employeeService.findEmployeeById(id);
        log.info("Responding to employee with id {}", id);
        return ResponseEntity.ok(employee);

    }

    @Override
    @GetMapping("/highestSalary")
    public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
        log.info("Get highest salary");
        Integer highestSalary = employeeService.findHighestSalaryOfEmployees();
        log.info("Successfully retrieved highest salary");
        return ResponseEntity.ok(highestSalary);
    }

    @Override
    @GetMapping("/topTenHighestEarning")
    public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
        log.info("Get top ten highest earning employees");
        List<String> topEarningEmployeeNames = employeeService.findTopTenHighestEarningEmployeeNames();
        log.info("Successfully retrieved top ten highest earning employees");
        return ResponseEntity.ok(topEarningEmployeeNames);
    }

    @Override
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Map<String, Object> employeeInput) {
        log.info("Create employee {}", employeeInput);
        Employee employee = Employee.builder()
                .employeeName((String) employeeInput.get("name"))
                .employeeSalary((String) employeeInput.get("salary"))
                .employeeAge((String) employeeInput.get("age"))
                .build();

        Employee createdEmployee = employeeService.newEmployee(employee);
        log.info("Successfully created employee");
        return ResponseEntity.ok(createdEmployee);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployeeById(@PathVariable String id) {
        log.info("Delete employee by id {}", id);
        String response = employeeService.removeEmployeeById(id);
        log.info("Successfully deleted employee");
        return ResponseEntity.ok(response);
    }
}
