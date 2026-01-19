package com.kabutar.auth.chat.api;

import com.kabutar.auth.chat.api.dto.FriendMessageResponse;
import com.kabutar.auth.chat.service.FriendChatService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/friends")
public class FriendChatApiController {

    private final FriendChatService friendChatService;

    public FriendChatApiController(FriendChatService friendChatService) {
        this.friendChatService = friendChatService;
    }

    @GetMapping("/{friendUsername}/messages")
    public List<FriendMessageResponse> getFriendMessages(
            @RequestHeader("X-Username") String me,
            @PathVariable String friendUsername,
            @RequestParam(defaultValue = "50") int limit
    ) {
        return friendChatService.getConversation(me, friendUsername, limit);
    }
}
