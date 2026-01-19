package com.kabutar.auth.chat.api.dto;

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

    public void setId(String id) { this.id = id; }
    public void setFromUser(String fromUser) { this.fromUser = fromUser; }
    public void setToUser(String toUser) { this.toUser = toUser; }
    public void setText(String text) { this.text = text; }
    public void setSentAt(Instant sentAt) { this.sentAt = sentAt; }
}
