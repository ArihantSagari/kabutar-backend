package com.kabutar.auth.dto;

public class LoginResponse {

    private Long userId;
    private String username;
    private String token; // temporary (later we do JWT cookies)

    public LoginResponse() {
    }

    public LoginResponse(Long userId, String username, String token) {
        this.userId = userId;
        this.username = username;
        this.token = token;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getToken() {
        return token;
    }
}
