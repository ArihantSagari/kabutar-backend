package com.kabutar.auth.chat.store;

import com.kabutar.auth.chat.friends.FriendRequest;
import com.kabutar.auth.chat.friends.FriendRequestStatus;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class UserDataStore {

    private final Map<String, Set<String>> friends = new ConcurrentHashMap<>();
    private final Map<String, List<FriendRequest>> incomingRequests = new ConcurrentHashMap<>();
    private final Map<String, Set<String>> blocked = new ConcurrentHashMap<>();

    // -------------------------
    // FRIENDS
    // -------------------------
    public Set<String> getFriends(String username) {
        if (username == null) return Collections.emptySet();
        String u = username.trim().toLowerCase();
        return friends.getOrDefault(u, Collections.emptySet());
    }

    public int getFriendsCount(String username) {
        return getFriends(username).size();
    }

    public void addFriendBothWays(String a, String b) {
        if (a == null || b == null) return;

        String u1 = a.trim().toLowerCase();
        String u2 = b.trim().toLowerCase();

        if (u1.isBlank() || u2.isBlank()) return;
        if (u1.equals(u2)) return;

        friends.computeIfAbsent(u1, k -> ConcurrentHashMap.newKeySet()).add(u2);
        friends.computeIfAbsent(u2, k -> ConcurrentHashMap.newKeySet()).add(u1);
    }

    public boolean areFriends(String a, String b) {
        if (a == null || b == null) return false;

        String u1 = a.trim().toLowerCase();
        String u2 = b.trim().toLowerCase();

        if (u1.isBlank() || u2.isBlank()) return false;
        if (u1.equals(u2)) return false;

        return friends.getOrDefault(u1, Collections.emptySet()).contains(u2)
                && friends.getOrDefault(u2, Collections.emptySet()).contains(u1);
    }

    // -------------------------
    // BLOCKS
    // -------------------------
    public Set<String> getBlocked(String username) {
        if (username == null) return Collections.emptySet();
        String u = username.trim().toLowerCase();
        return blocked.getOrDefault(u, Collections.emptySet());
    }

    public int getBlockedCount(String username) {
        return getBlocked(username).size();
    }

    // -------------------------
    // FRIEND REQUESTS
    // -------------------------
    public void createFriendRequest(String fromUser, String toUser) {
        if (fromUser == null || toUser == null) return;

        final String from = fromUser.trim().toLowerCase();
        final String to = toUser.trim().toLowerCase();

        if (from.isBlank() || to.isBlank()) return;
        if (from.equals(to)) return;

        if (areFriends(from, to)) return;

        List<FriendRequest> list = incomingRequests.computeIfAbsent(
                to,
                k -> Collections.synchronizedList(new ArrayList<>())
        );

        boolean existsPending = list.stream().anyMatch(r ->
                r.getFromUser().equalsIgnoreCase(from)
                        && r.getToUser().equalsIgnoreCase(to)
                        && r.getStatus() == FriendRequestStatus.PENDING
        );

        if (existsPending) return;

        list.add(new FriendRequest(from, to, FriendRequestStatus.PENDING, Instant.now()));
    }

    public List<FriendRequest> getIncomingRequests(String username) {
        if (username == null) return List.of();
        String u = username.trim().toLowerCase();
        return incomingRequests.getOrDefault(u, List.of());
    }

    public void acceptRequest(String toUser, String fromUser) {
        if (toUser == null || fromUser == null) return;

        final String to = toUser.trim().toLowerCase();
        final String from = fromUser.trim().toLowerCase();

        List<FriendRequest> list = incomingRequests.getOrDefault(to, List.of());

        for (FriendRequest r : list) {
            if (r.getFromUser().equalsIgnoreCase(from)
                    && r.getToUser().equalsIgnoreCase(to)
                    && r.getStatus() == FriendRequestStatus.PENDING) {

                r.setStatus(FriendRequestStatus.ACCEPTED);
                addFriendBothWays(to, from);
                return;
            }
        }
    }

    public void rejectRequest(String toUser, String fromUser) {
        if (toUser == null || fromUser == null) return;

        final String to = toUser.trim().toLowerCase();
        final String from = fromUser.trim().toLowerCase();

        List<FriendRequest> list = incomingRequests.getOrDefault(to, List.of());

        for (FriendRequest r : list) {
            if (r.getFromUser().equalsIgnoreCase(from)
                    && r.getToUser().equalsIgnoreCase(to)
                    && r.getStatus() == FriendRequestStatus.PENDING) {

                r.setStatus(FriendRequestStatus.REJECTED);
                return;
            }
        }
    }
}
