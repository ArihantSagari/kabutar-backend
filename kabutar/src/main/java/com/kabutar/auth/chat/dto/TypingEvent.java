package com.kabutar.auth.chat.dto;

public class TypingEvent {

    private String roomId;
    private String username;
    private boolean typing;

    public TypingEvent() {}

    public TypingEvent(String roomId, String username, boolean typing) {
        this.roomId = roomId;
        this.username = username;
        this.typing = typing;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getUsername() {
        return username;
    }

    public boolean isTyping() {
        return typing;
    }
}
