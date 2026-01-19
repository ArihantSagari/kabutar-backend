package com.kabutar.auth.chat.dto;

public class MatchFoundResponse {

    private String roomId;
    private String peerUsername;

    public MatchFoundResponse() {
    }

    public MatchFoundResponse(String roomId, String peerUsername) {
        this.roomId = roomId;
        this.peerUsername = peerUsername;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getPeerUsername() {
        return peerUsername;
    }
}
