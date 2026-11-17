package com.saksham.employee_management.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDTO {

private String token;
private String tokenType="Bearer";
private String username;
private String role;
}
