package com.kabutar.auth.chat.dto;

public class ChatMessageRequest {
    private String roomId;
    private String text;
    private String clientMessageId;

    public ChatMessageRequest() {}

    public ChatMessageRequest(String roomId, String text, String clientMessageId) {
        this.roomId = roomId;
        this.text = text;
        this.clientMessageId = clientMessageId;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getText() {
        return text;
    }

    public String getClientMessageId() {
        return clientMessageId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setClientMessageId(String clientMessageId) {
        this.clientMessageId = clientMessageId;
    }
}
