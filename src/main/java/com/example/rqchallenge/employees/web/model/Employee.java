package com.example.rqchallenge.employees.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee implements Serializable {

    private static final long serialVersionUID = -1679077499970363057L;

    @NotBlank
    @JsonProperty("id")
    private String id;

    @NotBlank
    @JsonProperty("employee_name")
    private String employeeName;

    @NotBlank
    @JsonProperty("employee_salary")
    private String employeeSalary;

    @NotBlank
    @JsonProperty("employee_age")
    private String employeeAge;

    @JsonProperty("profile_image")
    private String profileImage;


    @JsonProperty("name")
    private void setName(String name) {
        this.employeeName = name;
    }

    @JsonProperty("salary")
    private void setSalary(String salary) {
        this.employeeSalary = salary;
    }

    @JsonProperty("age")
    private void setAge(String age) {
        this.employeeAge = age;
    }

    @JsonProperty("id")
    private void setId(String id) {
        this.id = id;
    }
}
