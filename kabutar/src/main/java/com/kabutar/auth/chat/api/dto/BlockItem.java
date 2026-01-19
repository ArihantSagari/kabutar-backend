package com.kabutar.auth.chat.api.dto;

public class BlockItem {
    private String username;

    public BlockItem() {}

    public BlockItem(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
