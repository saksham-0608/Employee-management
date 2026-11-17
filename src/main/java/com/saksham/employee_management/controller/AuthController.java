package com.saksham.employee_management.controller;

import com.saksham.employee_management.dto.LoginRequestDTO;
import com.saksham.employee_management.dto.LoginResponseDTO;
import com.saksham.employee_management.dto.RegisterRequestDTO;
import com.saksham.employee_management.service.AuthService;
import com.saksham.employee_management.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

private final UserService userService;
    private final AuthService authService;


    @Autowired
    public AuthController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid  @RequestBody RegisterRequestDTO registerRequestDTO) {
        userService.registerUser(registerRequestDTO);

        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @Valid @RequestBody LoginRequestDTO request) {

        LoginResponseDTO response = authService.login(request);

        return ResponseEntity.ok(response);
    }

}
