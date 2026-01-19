package com.kabutar.auth.chat.api.dto;

public class FriendItem {
    private String username;

    public FriendItem() {}

    public FriendItem(String username) {
        this.username = username;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
}
