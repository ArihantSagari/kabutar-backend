package com.kabutar.auth.chat.service;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class InMemoryMatchmakingService implements MatchmakingService {

    private final Queue<String> waitingQueue = new ConcurrentLinkedQueue<>();

    // roomId -> pair
    private final Map<String, String[]> rooms = new ConcurrentHashMap<>();

    // roomId -> set of usernames who have sent at least 1 message
    private final Map<String, Set<String>> exchangeTracker = new ConcurrentHashMap<>();

    @Override
    public Optional<String> enqueue(String username) {
        waitingQueue.remove(username);

        String other = waitingQueue.poll();
        if (other == null) {
            waitingQueue.add(username);
            return Optional.empty();
        }

        if (other.equals(username)) {
            waitingQueue.add(username);
            return Optional.empty();
        }

        String roomId = UUID.randomUUID().toString();
        rooms.put(roomId, new String[]{username, other});
        exchangeTracker.put(roomId, ConcurrentHashMap.newKeySet());

        return Optional.of(roomId);
    }

    @Override
    public void removeIfWaiting(String username) {
        waitingQueue.remove(username);
    }

    @Override
    public Optional<String> getPeer(String roomId, String username) {
        String[] pair = rooms.get(roomId);
        if (pair == null) return Optional.empty();

        if (pair[0].equals(username)) return Optional.of(pair[1]);
        if (pair[1].equals(username)) return Optional.of(pair[0]);

        return Optional.empty();
    }

    @Override
    public void markSentMessage(String roomId, String username) {
        Set<String> set = exchangeTracker.get(roomId);
        if (set == null) return;
        set.add(username);
    }

    @Override
    public boolean canSendFriendRequest(String roomId) {
        String[] pair = rooms.get(roomId);
        if (pair == null) return false;

        Set<String> set = exchangeTracker.get(roomId);
        if (set == null) return false;

        // Both users must have sent at least 1 message
        return set.contains(pair[0]) && set.contains(pair[1]);
    }
}
