package com.saksham.employee_management.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeResponseDTO {

    private Long id;
    private String name;
    private String email;
    private Long departmentId;
    private String departmentName;
    private Double salary;
}
