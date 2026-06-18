package com.raizesdonordeste.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.raizesdonordeste.api.dto.auth.AuthResponse;
import com.raizesdonordeste.api.dto.auth.LoginRequest;
import com.raizesdonordeste.api.dto.auth.RegisterRequest;
import com.raizesdonordeste.application.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(
            @Valid @RequestBody RegisterRequest request) {

        authService.register(request);
    }
    
    @PostMapping("/login")
    public AuthResponse login(
            @Valid @RequestBody LoginRequest request) {

        return authService.login(request);
    }
    @GetMapping("/me")
    public String me() {
        return "Token válido";
    }
}