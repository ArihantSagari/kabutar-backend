package com.kabutar.auth.chat.dto;

import java.time.Instant;

public class FriendRequestEvent {
    private String roomId;
    private String fromUser;
    private String status;
    private Instant at;

    public FriendRequestEvent() {}

    public FriendRequestEvent(String roomId, String fromUser, String status, Instant at) {
        this.roomId = roomId;
        this.fromUser = fromUser;
        this.status = status;
        this.at = at;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getFromUser() {
        return fromUser;
    }

    public String getStatus() {
        return status;
    }

    public Instant getAt() {
        return at;
    }
}
