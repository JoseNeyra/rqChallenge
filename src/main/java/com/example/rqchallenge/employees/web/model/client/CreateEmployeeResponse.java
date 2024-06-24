package com.example.rqchallenge.employees.web.model.client;

import com.example.rqchallenge.employees.web.model.Employee;
import lombok.*;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateEmployeeResponse extends EmployeeClientResponseDto {

    private static final long serialVersionUID = 1682225811216136793L;

    @NotNull
    private Employee data;
}
