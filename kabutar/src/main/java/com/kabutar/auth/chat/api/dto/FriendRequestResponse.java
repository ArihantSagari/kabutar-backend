package com.kabutar.auth.chat.api.dto;

import com.kabutar.auth.chat.friends.FriendRequestStatus;

import java.time.Instant;

public class FriendRequestResponse {

    private String id;
    private String from;
    private String to;
    private FriendRequestStatus status;
    private Instant createdAt;

    public FriendRequestResponse() {}

    public FriendRequestResponse(String id, String from, String to, FriendRequestStatus status, Instant createdAt) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.status = status;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public FriendRequestStatus getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setStatus(FriendRequestStatus status) {
        this.status = status;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
