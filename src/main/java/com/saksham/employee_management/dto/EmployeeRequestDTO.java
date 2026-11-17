package com.saksham.employee_management.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeRequestDTO {
    @NotBlank(message = "Employee name is required")
    private String name;

    @NotBlank(message = "Employee email is required")
    @Email(message = "Please provide a valid email")
    private String email;

    @NotNull(message = "Department ID is required")
    private Long departmentId;

    @NotNull(message = "Salary is required")
    @Positive(message = "Salary must be greater than zero")
    private Double salary;
}
