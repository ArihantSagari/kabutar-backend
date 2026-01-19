package com.kabutar.auth.chat.api.dto;

public class RequestActionBody {
    private String requestId;

    public RequestActionBody() {}

    public RequestActionBody(String requestId) {
        this.requestId = requestId;
    }

    public String getRequestId() { return requestId; }
    public void setRequestId(String requestId) { this.requestId = requestId; }
}
