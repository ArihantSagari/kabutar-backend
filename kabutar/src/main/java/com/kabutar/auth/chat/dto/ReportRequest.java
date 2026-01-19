package com.kabutar.auth.chat.dto;

public class ReportRequest {

    private String roomId;
    private String reason; // e.g. "ABUSE", "SPAM", "INAPPROPRIATE", "SCAM"

    public ReportRequest() {}

    public ReportRequest(String roomId, String reason) {
        this.roomId = roomId;
        this.reason = reason;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getReason() {
        return reason;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
