package com.kabutar.auth.chat.dto;

public class StartChatRequest {

    private String username;

    public StartChatRequest() {
    }

    public StartChatRequest(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
