package com.example.rqchallenge.employees.web.model.client;

import lombok.Data;

import java.io.Serializable;


@Data
public abstract class EmployeeClientResponseDto implements Serializable {

    protected String status;
}
