package com.example.rqchallenge.employees.web.model.client;

import com.example.rqchallenge.employees.web.model.Employee;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeesResponse extends EmployeeClientResponseDto {

    private static final long serialVersionUID = -6505866045663612727L;

    @NotNull
    private List<Employee> data;
}
