package com.kabutar.auth.chat.dto;

import java.util.List;

public class FriendsResponse {
    private List<String> friends;
    private int count;

    public FriendsResponse() {}

    public FriendsResponse(List<String> friends) {
        this.friends = friends;
        this.count = friends.size();
    }

    public List<String> getFriends() { return friends; }
    public int getCount() { return count; }

    public void setFriends(List<String> friends) {
        this.friends = friends;
        this.count = friends == null ? 0 : friends.size();
    }
}
