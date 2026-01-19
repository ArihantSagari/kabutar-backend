package com.kabutar.auth.chat.dto;

public class SeenRequest {
    private String roomId;

    public SeenRequest() {}

    public SeenRequest(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
}
