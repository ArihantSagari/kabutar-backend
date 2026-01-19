package com.kabutar.auth.chat.api;

import org.springframework.stereotype.Component;

@Component
public class UserContext {
    public String getUsername(String headerUsername) {
        if (headerUsername == null || headerUsername.isBlank()) return "user";
        return headerUsername.trim().toLowerCase();
    }
}
