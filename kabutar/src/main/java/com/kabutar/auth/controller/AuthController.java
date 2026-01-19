package com.kabutar.auth.controller;

import com.kabutar.auth.dto.*;
import com.kabutar.auth.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/username-available")
    public UsernameAvailableResponse usernameAvailable(@RequestParam("username") String username) {
        boolean available = authService.isUsernameAvailable(username);
        return new UsernameAvailableResponse(available);
    }

    @PostMapping("/register")
    public RegisterResponse register(@Valid @RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }
}
