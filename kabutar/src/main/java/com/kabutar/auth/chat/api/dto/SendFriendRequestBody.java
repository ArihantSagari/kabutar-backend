package com.kabutar.auth.chat.api.dto;

public class SendFriendRequestBody {
    private String to;

    public SendFriendRequestBody() {}

    public SendFriendRequestBody(String to) {
        this.to = to;
    }

    public String getTo() { return to; }
    public void setTo(String to) { this.to = to; }
}
