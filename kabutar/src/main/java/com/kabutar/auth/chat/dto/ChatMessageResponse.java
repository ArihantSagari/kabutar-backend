package com.kabutar.auth.chat.dto;

import java.time.Instant;

public class ChatMessageResponse {
    private String roomId;
    private String sender;
    private String text;
    private Instant sentAt;

    private String messageId;       // server generated
    private String clientMessageId; // echoed back

    public ChatMessageResponse() {}

    public ChatMessageResponse(String roomId, String sender, String text, Instant sentAt,
                               String messageId, String clientMessageId) {
        this.roomId = roomId;
        this.sender = sender;
        this.text = text;
        this.sentAt = sentAt;
        this.messageId = messageId;
        this.clientMessageId = clientMessageId;
    }

    public String getRoomId() { return roomId; }
    public String getSender() { return sender; }
    public String getText() { return text; }
    public Instant getSentAt() { return sentAt; }
    public String getMessageId() { return messageId; }
    public String getClientMessageId() { return clientMessageId; }

    public void setRoomId(String roomId) { this.roomId = roomId; }
    public void setSender(String sender) { this.sender = sender; }
    public void setText(String text) { this.text = text; }
    public void setSentAt(Instant sentAt) { this.sentAt = sentAt; }
    public void setMessageId(String messageId) { this.messageId = messageId; }
    public void setClientMessageId(String clientMessageId) { this.clientMessageId = clientMessageId; }
}
