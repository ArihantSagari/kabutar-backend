package com.kabutar.auth.chat.dto;

import java.time.Instant;

public class SystemEvent {

    private String roomId;
    private String type; // "DISCONNECTED", "REPORT_RECEIVED", "FRIEND_REQUESTED", "FRIEND_ACCEPTED"
    private String message;
    private Instant at;

    public SystemEvent() {}

    public SystemEvent(String roomId, String type, String message, Instant at) {
        this.roomId = roomId;
        this.type = type;
        this.message = message;
        this.at = at;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public Instant getAt() {
        return at;
    }
}
