package com.kabutar.auth.chat.service;

import java.util.Optional;

public interface MatchmakingService {

    Optional<String> enqueue(String username);

    void removeIfWaiting(String username);

    Optional<String> getPeer(String roomId, String username);

    void markSentMessage(String roomId, String username);

    boolean canSendFriendRequest(String roomId);
}
