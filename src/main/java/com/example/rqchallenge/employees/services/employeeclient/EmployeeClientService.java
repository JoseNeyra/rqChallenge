package com.example.rqchallenge.employees.services.employeeclient;

import com.example.rqchallenge.employees.exceptions.InvalidClientResponseException;
import com.example.rqchallenge.employees.web.model.Employee;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Map;

public interface EmployeeClientService {

    List<Employee> getAllEmployees() throws HttpClientErrorException, InvalidClientResponseException;

    Employee getEmployeeById(String id) throws HttpClientErrorException, InvalidClientResponseException;

    Employee createEmployee(Map<String, Object> employee) throws HttpClientErrorException, InvalidClientResponseException;

    String deleteEmployeeById(String id) throws HttpClientErrorException, InvalidClientResponseException;
}
