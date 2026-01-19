package com.kabutar.auth.chat.api.dto;

public class MeResponse {
    private String username;
    private int friendsCount;
    private int blockedCount;
    private int streakDays;
    private int mostFriends;

    public MeResponse(String username, int friendsCount, int blockedCount, int streakDays, int mostFriends) {
        this.username = username;
        this.friendsCount = friendsCount;
        this.blockedCount = blockedCount;
        this.streakDays = streakDays;
        this.mostFriends = mostFriends;
    }

    public String getUsername() { return username; }
    public int getFriendsCount() { return friendsCount; }
    public int getBlockedCount() { return blockedCount; }
    public int getStreakDays() { return streakDays; }
    public int getMostFriends() { return mostFriends; }
}
