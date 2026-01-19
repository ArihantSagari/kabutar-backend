package com.kabutar.auth.chat.dto;

import java.time.Instant;

public class MessageStatusEvent {
    private String roomId;
    private String messageId;
    private String status; // DELIVERED | SEEN
    private String byUser;
    private Instant at;

    public MessageStatusEvent() {}

    public MessageStatusEvent(String roomId, String messageId, String status, String byUser, Instant at) {
        this.roomId = roomId;
        this.messageId = messageId;
        this.status = status;
        this.byUser = byUser;
        this.at = at;
    }

    public String getRoomId() { return roomId; }
    public String getMessageId() { return messageId; }
    public String getStatus() { return status; }
    public String getByUser() { return byUser; }
    public Instant getAt() { return at; }
}
