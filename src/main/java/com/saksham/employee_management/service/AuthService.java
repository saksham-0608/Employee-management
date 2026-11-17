package com.saksham.employee_management.service;
import com.saksham.employee_management.dto.LoginRequestDTO;
import com.saksham.employee_management.dto.LoginResponseDTO;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(AuthenticationManager authenticationManager,
                       JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public LoginResponseDTO login(LoginRequestDTO request) {

        // Authenticate username and password
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        // Generate JWT token after successful authentication
        String token = jwtService.generateToken(
                authentication.getName()
        );

        // Prepare login response
        LoginResponseDTO response = new LoginResponseDTO();

        response.setToken(token);
        response.setUsername(authentication.getName());

        response.setRole(authentication.getAuthorities()
                .iterator()
                .next()
                .getAuthority()
                .replace("ROLE_", ""));

        return response;
    }
}