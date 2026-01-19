package com.kabutar.auth.chat.dto;

public class TypingRequest {

    private String roomId;

    public TypingRequest() {}

    public TypingRequest(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
}
