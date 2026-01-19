package com.kabutar.auth.chat.friends;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name = "friend_messages")
public class FriendMessage {

    @Id
    private String id;

    private String fromUser;
    private String toUser;
    private String text;
    private Instant sentAt;

    public FriendMessage() {}

    public FriendMessage(String id, String fromUser, String toUser, String text, Instant sentAt) {
        this.id = id;
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.text = text;
        this.sentAt = sentAt;
    }

    public String getId() { return id; }
    public String getFromUser() { return fromUser; }
    public String getToUser() { return toUser; }
    public String getText() { return text; }
    public Instant getSentAt() { return sentAt; }
}
