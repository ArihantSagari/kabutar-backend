package com.kabutar.auth.service;

import com.kabutar.auth.dto.LoginRequest;
import com.kabutar.auth.dto.LoginResponse;
import com.kabutar.auth.dto.RegisterRequest;
import com.kabutar.auth.dto.RegisterResponse;

public interface AuthService {

    boolean isUsernameAvailable(String username);

    RegisterResponse register(RegisterRequest request);

    LoginResponse login(LoginRequest request);
}
