package com.example.rqchallenge.employees.services.employee;

import com.example.rqchallenge.employees.exceptions.HttpClientServiceException;
import com.example.rqchallenge.employees.services.employeeclient.EmployeeClientService;
import com.example.rqchallenge.employees.web.model.Employee;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeClientService employeeClientService;

    @Override
    public List<Employee> findAllEmployees() {
        log.debug("Find all employees");
            return employeeClientService.getAllEmployees();
    }

    @Override
    public List<Employee> findEmployeesByNameSearch(String name) {
        log.debug("Find employees by name {}", name);
            return employeeClientService.getAllEmployees().stream()
                    .filter(employee -> employee.getEmployeeName().toLowerCase().contains(name.toLowerCase()))
                    .collect(Collectors.toList());
    }

    @Override
    public Employee findEmployeeById(String id) {
        log.debug("Find employee by id {}", id);
            return employeeClientService.getEmployeeById(id);
    }

    @Override
    public Integer findHighestSalaryOfEmployees() {
        log.debug("Find highest salary of employees");
            return employeeClientService.getAllEmployees().stream()
                    .map(Employee::getEmployeeSalary)
                    .mapToInt(Integer::parseInt)
                    .max()
                    .orElse(0);
    }

    @Override
    public List<String> findTopTenHighestEarningEmployeeNames() {
        log.debug("Find top 10 highest earning employees");
            return employeeClientService.getAllEmployees().stream()
                    .sorted(Comparator.comparingInt((Employee e) -> Integer.parseInt(e.getEmployeeSalary())).reversed())
                    .limit(10)
                    .map(Employee::getEmployeeName)
                    .collect(Collectors.toList());
    }

    @Override
    public Employee newEmployee(Employee employeeToCreate) {
        log.debug("Create new employee {}", employeeToCreate);
            return employeeClientService.createEmployee(Map.of(
                    "name", employeeToCreate.getEmployeeName(),
                    "salary", employeeToCreate.getEmployeeSalary(),
                    "age", employeeToCreate.getEmployeeAge()
            ));
    }

    @Override
    public String removeEmployeeById(String id) {
        log.debug("Remove employee by id {}", id);
            return employeeClientService.deleteEmployeeById(id);
    }
}
