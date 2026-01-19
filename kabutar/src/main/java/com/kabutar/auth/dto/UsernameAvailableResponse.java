package com.kabutar.auth.dto;

public class UsernameAvailableResponse {

    private boolean available;

    public UsernameAvailableResponse() {
    }

    public UsernameAvailableResponse(boolean available) {
        this.available = available;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
