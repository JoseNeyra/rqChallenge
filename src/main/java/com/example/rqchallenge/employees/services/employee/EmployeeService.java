package com.example.rqchallenge.employees.services.employee;

import com.example.rqchallenge.employees.web.model.Employee;

import java.util.List;


public interface EmployeeService {

    List<Employee> findAllEmployees();

    List<Employee> findEmployeesByNameSearch(String name);

    Employee findEmployeeById(String id);

    Integer findHighestSalaryOfEmployees();

    List<String> findTopTenHighestEarningEmployeeNames();

    Employee newEmployee(Employee employeeToCreate);

    String removeEmployeeById(String id);
}
