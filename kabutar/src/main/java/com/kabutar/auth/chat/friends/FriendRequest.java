package com.kabutar.auth.chat.friends;

import java.time.Instant;

public class FriendRequest {
    private String fromUser;
    private String toUser;
    private FriendRequestStatus status;
    private Instant createdAt;

    public FriendRequest() {}

    public FriendRequest(String fromUser, String toUser, FriendRequestStatus status, Instant createdAt) {
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.status = status;
        this.createdAt = createdAt;
    }

    public String getFromUser() { return fromUser; }
    public String getToUser() { return toUser; }
    public FriendRequestStatus getStatus() { return status; }
    public Instant getCreatedAt() { return createdAt; }

    public void setStatus(FriendRequestStatus status) { this.status = status; }
}
