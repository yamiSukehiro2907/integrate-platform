package com.integrate.identity.controllers;

import com.integrate.identity.dto.LoginRequest;
import com.integrate.identity.dto.SignUpRequest;
import com.integrate.identity.services.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        SignUpRequest cleaned = new SignUpRequest(signUpRequest.name().trim(), signUpRequest.email().trim(), signUpRequest.password().trim());
        return authService.registerUser(cleaned);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        LoginRequest cleaned = new LoginRequest(loginRequest.email().trim(), loginRequest.password().trim());
        return authService.loginUser(cleaned, response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshUser(HttpServletRequest request, HttpServletResponse response) {
        return authService.refreshUser(request , response);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(HttpServletRequest request, HttpServletResponse response) {
        return authService.logoutUser(request , response);
    }
}
