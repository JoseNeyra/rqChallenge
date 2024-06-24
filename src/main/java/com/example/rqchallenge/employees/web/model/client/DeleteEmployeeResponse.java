package com.example.rqchallenge.employees.web.model.client;

import lombok.*;

import javax.validation.constraints.NotNull;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeleteEmployeeResponse extends EmployeeClientResponseDto {

    private static final long serialVersionUID = -1326674001157581253L;

    @NotNull
    private String message;
}
