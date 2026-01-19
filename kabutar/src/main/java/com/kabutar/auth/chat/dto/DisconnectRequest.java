package com.kabutar.auth.chat.dto;

public class DisconnectRequest {

    private String roomId;

    public DisconnectRequest() {}

    public DisconnectRequest(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
}
