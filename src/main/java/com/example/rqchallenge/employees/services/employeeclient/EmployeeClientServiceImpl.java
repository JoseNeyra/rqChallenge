package com.example.rqchallenge.employees.services.employeeclient;

import com.example.rqchallenge.employees.exceptions.InvalidClientResponseException;
import com.example.rqchallenge.employees.web.model.Employee;
import com.example.rqchallenge.employees.web.model.client.CreateEmployeeResponse;
import com.example.rqchallenge.employees.web.model.client.DeleteEmployeeResponse;
import com.example.rqchallenge.employees.web.model.client.EmployeeResponse;
import com.example.rqchallenge.employees.web.model.client.EmployeesResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Slf4j
@RequiredArgsConstructor
@Service
public class EmployeeClientServiceImpl implements EmployeeClientService {

    private final RestTemplateBuilder restTemplateBuilder;
    private final Validator validator;
    private RestTemplate restTemplate;

    @PostConstruct
    public void init() {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public List<Employee> getAllEmployees() throws InvalidClientResponseException {
        EmployeesResponse response = restTemplate.getForObject("/api/v1/employees", EmployeesResponse.class);
        if (response != null && "success".equals(response.getStatus())) {
            validateEmployees(response.getData());
            return response.getData();
        } else {
            throw new InvalidClientResponseException("Invalid response from API");
        }
    }

    @Override
    public Employee getEmployeeById(String id) throws InvalidClientResponseException {
        EmployeeResponse response = restTemplate.getForObject("/api/v1/employee/{id}", EmployeeResponse.class, id);
        if (response != null && "success".equals(response.getStatus())) {
            validateEmployee(response.getData());
            return response.getData();
        } else {
            throw new InvalidClientResponseException("Invalid response from API");
        }
    }

    @Override
    public Employee createEmployee(Map<String, Object> employee) throws InvalidClientResponseException {
        CreateEmployeeResponse response = restTemplate.postForObject("/api/v1/create", employee, CreateEmployeeResponse.class);
        if (response != null && "success".equals(response.getStatus())) {
            validateEmployee(response.getData());
            return response.getData();
        } else {
            throw new InvalidClientResponseException("Invalid response from API");
        }
    }

    @Override
    public String deleteEmployeeById(String id) throws InvalidClientResponseException {
        String url = "/api/v1/delete/{id}";
        ResponseEntity<DeleteEmployeeResponse> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.DELETE,
                null,
                DeleteEmployeeResponse.class,
                id
        );

        DeleteEmployeeResponse response = responseEntity.getBody();
        if (response != null && "success".equals(response.getStatus())) {
            return response.getMessage();
        } else {
            throw new InvalidClientResponseException("Invalid response from API");
        }
    }

    private void validateEmployee(Employee employee) {
        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<Employee> violation : violations) {
                log.error("Validation error: {} - Invalid value: {}", violation.getMessage(), violation.getInvalidValue());
            }
            throw new InvalidClientResponseException("Employee validation failed: " + violations);
        }
    }

    private void validateEmployees(List<Employee> employees) {
        for (Employee employee : employees) {
            validateEmployee(employee);
        }
    }
}
