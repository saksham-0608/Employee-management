package com.saksham.employee_management.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDTO {

@NotBlank(message = "username is required")
private String username;

@NotBlank(message = "password is required")
private String password;
}
