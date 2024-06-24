package com.example.rqchallenge.employees.web.model.client;

import com.example.rqchallenge.employees.web.model.Employee;
import lombok.*;

import javax.validation.constraints.NotNull;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeResponse extends EmployeeClientResponseDto {

    private static final long serialVersionUID = -7928938167416167797L;

    @NotNull
    private Employee data;
}
