package com.kabutar.auth.chat.controller;

import com.kabutar.auth.chat.api.dto.FriendMessageResponse;
import com.kabutar.auth.chat.dto.*;
import com.kabutar.auth.chat.friends.FriendMessage;
import com.kabutar.auth.chat.friends.FriendMessageRepository;
import com.kabutar.auth.chat.service.MatchmakingService;
import com.kabutar.auth.chat.store.UserDataStore;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Controller
public class ChatWsController {

    private final MatchmakingService matchmakingService;
    private final SimpMessagingTemplate messagingTemplate;
    private final UserDataStore store;
    private final FriendMessageRepository friendMessageRepo;

    public ChatWsController(
            MatchmakingService matchmakingService,
            SimpMessagingTemplate messagingTemplate,
            UserDataStore store,
            FriendMessageRepository friendMessageRepo
    ) {
        this.matchmakingService = matchmakingService;
        this.messagingTemplate = messagingTemplate;
        this.store = store;
        this.friendMessageRepo = friendMessageRepo;
    }

    // -----------------------------
    // RANDOM MATCH CHAT
    // -----------------------------
    @MessageMapping("/chat.start")
    public void startChat(StartChatRequest request, Principal principal) {
        String username = principal.getName();

        Optional<String> roomOpt = matchmakingService.enqueue(username);
        if (roomOpt.isEmpty()) return;

        String roomId = roomOpt.get();

        Optional<String> peerOpt = matchmakingService.getPeer(roomId, username);
        if (peerOpt.isEmpty()) return;

        String peer = peerOpt.get();

        messagingTemplate.convertAndSendToUser(
                username,
                "/queue/match",
                new MatchFoundResponse(roomId, peer)
        );

        messagingTemplate.convertAndSendToUser(
                peer,
                "/queue/match",
                new MatchFoundResponse(roomId, username)
        );
    }

    @MessageMapping("/chat.send")
    public void send(ChatMessageRequest req, Principal principal) {
        String sender = principal.getName();
        if (req == null || req.getRoomId() == null || req.getRoomId().isBlank()) return;
        if (req.getText() == null || req.getText().trim().isBlank()) return;

        String messageId = UUID.randomUUID().toString();

        ChatMessageResponse res = new ChatMessageResponse(
                req.getRoomId(),
                sender,
                req.getText().trim(),
                Instant.now(),
                messageId,
                req.getClientMessageId()
        );

        messagingTemplate.convertAndSend("/topic/room/" + req.getRoomId(), res);
    }

    @MessageMapping("/chat.typing")
    public void typing(TypingRequest request, Principal principal) {
        String username = principal.getName();
        if (request == null || request.getRoomId() == null || request.getRoomId().isBlank()) return;

        messagingTemplate.convertAndSend(
                "/topic/room/" + request.getRoomId(),
                new TypingEvent(request.getRoomId(), username, true)
        );
    }

    @MessageMapping("/chat.stopTyping")
    public void stopTyping(TypingRequest request, Principal principal) {
        String username = principal.getName();
        if (request == null || request.getRoomId() == null || request.getRoomId().isBlank()) return;

        messagingTemplate.convertAndSend(
                "/topic/room/" + request.getRoomId(),
                new TypingEvent(request.getRoomId(), username, false)
        );
    }

    @MessageMapping("/chat.disconnect")
    public void disconnect(DisconnectRequest request, Principal principal) {
        if (request == null || request.getRoomId() == null || request.getRoomId().isBlank()) return;

        messagingTemplate.convertAndSend(
                "/topic/room/" + request.getRoomId(),
                new SystemEvent(request.getRoomId(), "DISCONNECTED",
                        "Stranger left the chat.", Instant.now())
        );
    }

    // -----------------------------
    // FRIEND REQUEST (CARD EVENT)
    // -----------------------------
    @MessageMapping("/chat.friend.request")
    public void friendRequest(FriendRequestDto request, Principal principal) {
        String fromUser = principal.getName();
        if (request == null || request.getRoomId() == null || request.getRoomId().isBlank()) return;

        Optional<String> peerOpt = matchmakingService.getPeer(request.getRoomId(), fromUser);
        if (peerOpt.isEmpty()) return;

        String toUser = peerOpt.get();

        // store pending request
        store.createFriendRequest(fromUser, toUser);

        // receiver sees request card
        messagingTemplate.convertAndSendToUser(
                toUser,
                "/queue/friend",
                new FriendRequestEvent(request.getRoomId(), fromUser, "REQUESTED", Instant.now())
        );

        // sender sees "request sent"
        messagingTemplate.convertAndSendToUser(
                fromUser,
                "/queue/friend",
                new FriendRequestEvent(request.getRoomId(), toUser, "REQUESTED_SENT", Instant.now())
        );
    }

    @MessageMapping("/chat.friend.accept")
    public void friendAccept(FriendRequestDto request, Principal principal) {
        String toUser = principal.getName();
        if (request == null || request.getRoomId() == null || request.getRoomId().isBlank()) return;

        Optional<String> peerOpt = matchmakingService.getPeer(request.getRoomId(), toUser);
        if (peerOpt.isEmpty()) return;

        String fromUser = peerOpt.get();

        store.acceptRequest(toUser, fromUser);

        messagingTemplate.convertAndSendToUser(
                toUser,
                "/queue/friend",
                new FriendRequestEvent(request.getRoomId(), fromUser, "ACCEPTED", Instant.now())
        );

        messagingTemplate.convertAndSendToUser(
                fromUser,
                "/queue/friend",
                new FriendRequestEvent(request.getRoomId(), toUser, "ACCEPTED", Instant.now())
        );
    }

    @MessageMapping("/chat.friend.reject")
    public void friendReject(FriendRequestDto request, Principal principal) {
        String toUser = principal.getName();
        if (request == null || request.getRoomId() == null || request.getRoomId().isBlank()) return;

        Optional<String> peerOpt = matchmakingService.getPeer(request.getRoomId(), toUser);
        if (peerOpt.isEmpty()) return;

        String fromUser = peerOpt.get();

        store.rejectRequest(toUser, fromUser);

        messagingTemplate.convertAndSendToUser(
                toUser,
                "/queue/friend",
                new FriendRequestEvent(request.getRoomId(), fromUser, "REJECTED", Instant.now())
        );

        messagingTemplate.convertAndSendToUser(
                fromUser,
                "/queue/friend",
                new FriendRequestEvent(request.getRoomId(), toUser, "REJECTED", Instant.now())
        );
    }

    // -----------------------------
    // FRIEND PERMANENT CHAT (DB)
    // -----------------------------
    @MessageMapping("/friend.send")
    public void sendFriendMessage(FriendMessageRequest req, Principal principal) {
        String fromUser = principal.getName();
        if (req == null) return;

        String toUser = req.getToUsername();
        String text = req.getText();

        if (toUser == null || toUser.isBlank()) return;
        if (text == null || text.trim().isBlank()) return;

        // must be friends
        if (!store.areFriends(fromUser, toUser)) {
            messagingTemplate.convertAndSendToUser(
                    fromUser,
                    "/queue/system",
                    new SystemEvent(null, "NOT_FRIENDS",
                            "You can chat only after becoming friends.", Instant.now())
            );
            return;
        }

        String id = UUID.randomUUID().toString();
        Instant now = Instant.now();

        FriendMessage saved = friendMessageRepo.save(
                new FriendMessage(id, fromUser, toUser, text.trim(), now)
        );

        FriendMessageResponse payload = new FriendMessageResponse(
                saved.getId(),
                saved.getFromUser(),
                saved.getToUser(),
                saved.getText(),
                saved.getSentAt()
        );

        // receiver gets message instantly
        messagingTemplate.convertAndSendToUser(
                toUser,
                "/queue/friend/messages",
                payload
        );

        // sender also receives (for instant UI update)
        messagingTemplate.convertAndSendToUser(
                fromUser,
                "/queue/friend/messages",
                payload
        );
    }
}
