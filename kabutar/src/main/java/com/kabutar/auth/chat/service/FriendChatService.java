package com.kabutar.auth.chat.service;

import com.kabutar.auth.chat.api.dto.FriendMessageResponse;
import com.kabutar.auth.chat.friends.FriendMessage;
import com.kabutar.auth.chat.friends.FriendMessageRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class FriendChatService {

    private final FriendMessageRepository repo;

    public FriendChatService(FriendMessageRepository repo) {
        this.repo = repo;
    }

    public FriendMessage save(String fromUser, String toUser, String text) {
        String id = UUID.randomUUID().toString();
        Instant now = Instant.now();
        return repo.save(new FriendMessage(id, fromUser, toUser, text, now));
    }

    public List<FriendMessageResponse> getConversation(String u1, String u2, int limit) {
        return repo.findConversation(u1, u2, PageRequest.of(0, limit))
                .stream()
                .map(m -> new FriendMessageResponse(
                        m.getId(),
                        m.getFromUser(),
                        m.getToUser(),
                        m.getText(),
                        m.getSentAt()
                ))
                .toList();
    }
}
