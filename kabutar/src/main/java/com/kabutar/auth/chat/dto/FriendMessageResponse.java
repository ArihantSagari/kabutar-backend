package com.kabutar.auth.chat.dto;

import java.time.Instant;

public class FriendMessageResponse {
    private String id;
    private String fromUser;
    private String toUser;
    private String text;
    private Instant sentAt;

    public FriendMessageResponse() {}

    public FriendMessageResponse(String id, String fromUser, String toUser, String text, Instant sentAt) {
        this.id = id;
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.text = text;
        this.sentAt = sentAt;
    }

    public String getId() { return id; }
    public String getFromUser() { return fromUser; }
    public String getToUser() { return toUser; }
    public String getText() { return text; }
    public Instant getSentAt() { return sentAt; }
}
