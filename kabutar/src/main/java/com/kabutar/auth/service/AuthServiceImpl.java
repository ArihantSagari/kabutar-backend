package com.kabutar.auth.service;

import com.kabutar.auth.dto.LoginRequest;
import com.kabutar.auth.dto.LoginResponse;
import com.kabutar.auth.dto.RegisterRequest;
import com.kabutar.auth.dto.RegisterResponse;
import com.kabutar.auth.entity.User;
import com.kabutar.auth.exception.BadRequestException;
import com.kabutar.auth.exception.ConflictException;
import com.kabutar.auth.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean isUsernameAvailable(String username) {
        String u = normalizeUsername(username);

        if (!isValidUsername(u)) {
            return false;
        }

        return !userRepository.existsByUsernameIgnoreCase(u);
    }

    @Override
    public RegisterResponse register(RegisterRequest request) {
        String username = normalizeUsername(request.getUsername());
        String email = normalizeEmail(request.getEmail());

        if (!isValidUsername(username)) {
            throw new BadRequestException("username must be 3–20 characters and contain only letters, numbers, underscore");
        }

        if (userRepository.existsByUsernameIgnoreCase(username)) {
            throw new ConflictException("username already taken");
        }

        if (userRepository.existsByEmailIgnoreCase(email)) {
            throw new ConflictException("email already registered");
        }

        String passwordHash = passwordEncoder.encode(request.getPassword());

        User user = new User(username, email, passwordHash, Instant.now());
        User saved = userRepository.save(user);

        return new RegisterResponse(saved.getId(), saved.getUsername(), saved.getEmail());
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        String identifier = request.getIdentifier() == null ? "" : request.getIdentifier().trim().toLowerCase();
        String password = request.getPassword() == null ? "" : request.getPassword();

        if (identifier.isBlank()) {
            throw new BadRequestException("identifier is required");
        }

        Optional<User> userOpt;

        if (identifier.contains("@")) {
            userOpt = userRepository.findByEmailIgnoreCase(identifier);
        } else {
            userOpt = userRepository.findByUsernameIgnoreCase(identifier);
        }

        if (userOpt.isEmpty()) {
            // secure response (do not reveal user existence)
            throw new BadRequestException("Invalid credentials");
        }

        User user = userOpt.get();

        boolean ok = passwordEncoder.matches(password, user.getPasswordHash());
        if (!ok) {
            throw new BadRequestException("Invalid credentials");
        }

        // TEMP TOKEN (for now)
        // Later: JWT access + refresh token (secure)
        String token = UUID.randomUUID().toString();

        return new LoginResponse(user.getId(), user.getUsername(), token);
    }

    private String normalizeUsername(String username) {
        if (username == null) return "";
        return username.trim().toLowerCase();
    }

    private String normalizeEmail(String email) {
        if (email == null) return "";
        return email.trim().toLowerCase();
    }

    private boolean isValidUsername(String username) {
        if (username == null) return false;
        if (username.length() < 3 || username.length() > 20) return false;
        return username.matches("^[a-z0-9_]+$");
    }
}
