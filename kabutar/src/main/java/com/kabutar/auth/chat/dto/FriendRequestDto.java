package com.kabutar.auth.chat.dto;

public class FriendRequestDto {

    private String roomId;

    public FriendRequestDto() {}

    public FriendRequestDto(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
}
