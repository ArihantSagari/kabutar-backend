package com.kabutar.auth.chat.api;

import com.kabutar.auth.chat.api.dto.FriendItem;
import com.kabutar.auth.chat.store.UserDataStore;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/friends")
public class FriendsApiController {

    private final UserDataStore store;

    public FriendsApiController(UserDataStore store) {
        this.store = store;
    }

    private String requireUsername(String username) {
        if (username == null || username.isBlank()) throw new RuntimeException("X-Username missing");
        return username.trim();
    }

    @GetMapping
    public List<FriendItem> getFriends(
            @RequestHeader(value = "X-Username", required = false) String username
    ) {
        username = requireUsername(username);

        List<String> list = new ArrayList<>(store.getFriends(username));
        list.sort(String::compareToIgnoreCase);

        List<FriendItem> out = new ArrayList<>();
        for (String f : list) {
            out.add(new FriendItem(f));
        }
        return out;
    }
}
