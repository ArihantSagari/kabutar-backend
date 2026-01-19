package com.kabutar.auth.chat.dto;

public class FriendMessageRequest {

    private String toUsername;
    private String text;
    private String clientMessageId;

    public FriendMessageRequest() {}

    public FriendMessageRequest(String toUsername, String text, String clientMessageId) {
        this.toUsername = toUsername;
        this.text = text;
        this.clientMessageId = clientMessageId;
    }

    public String getToUsername() {
        return toUsername;
    }

    public void setToUsername(String toUsername) {
        this.toUsername = toUsername;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getClientMessageId() {
        return clientMessageId;
    }

    public void setClientMessageId(String clientMessageId) {
        this.clientMessageId = clientMessageId;
    }
}
