package com.kabutar.auth.chat.dto;

public class DeliveryAckRequest {
    private String roomId;
    private String messageId;

    public DeliveryAckRequest() {}

    public DeliveryAckRequest(String roomId, String messageId) {
        this.roomId = roomId;
        this.messageId = messageId;
    }

    public String getRoomId() { return roomId; }
    public String getMessageId() { return messageId; }

    public void setRoomId(String roomId) { this.roomId = roomId; }
    public void setMessageId(String messageId) { this.messageId = messageId; }
}
