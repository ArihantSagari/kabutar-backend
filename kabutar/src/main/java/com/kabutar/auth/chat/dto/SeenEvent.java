package com.kabutar.auth.chat.dto;

import java.time.Instant;

public class SeenEvent {

    private String roomId;
    private String username; // who saw
    private Instant seenAt;

    public SeenEvent() {}

    public SeenEvent(String roomId, String username, Instant seenAt) {
        this.roomId = roomId;
        this.username = username;
        this.seenAt = seenAt;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getUsername() {
        return username;
    }

    public Instant getSeenAt() {
        return seenAt;
    }
}
