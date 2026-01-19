package com.kabutar.auth.chat.api;

import com.kabutar.auth.chat.api.dto.AddCustomTopicRequest;
import com.kabutar.auth.chat.api.dto.BlockItem;
import com.kabutar.auth.chat.api.dto.MeResponse;
import com.kabutar.auth.chat.api.dto.OkResponse;
import com.kabutar.auth.chat.api.dto.PreferencesResponse;
import com.kabutar.auth.chat.api.dto.SavePreferencesRequest;
import com.kabutar.auth.chat.store.UserDataStore;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserApiController {

    private final UserDataStore store;
    private final PreferencesService preferencesService;

    public UserApiController(UserDataStore store, PreferencesService preferencesService) {
        this.store = store;
        this.preferencesService = preferencesService;
    }

    private String requireUsername(String username) {
        if (username == null || username.isBlank()) {
            throw new RuntimeException("X-Username header missing");
        }
        return username.trim();
    }

    // -------------------------
    // ME (stats)
    // -------------------------
    @GetMapping("/me")
    public MeResponse me(@RequestHeader(value = "X-Username", required = false) String username) {
        username = requireUsername(username);

        int friendsCount = store.getFriendsCount(username);
        int blockedCount = store.getBlockedCount(username);

        int streakDays = 0;
        int mostFriends = friendsCount;

        return new MeResponse(username, friendsCount, blockedCount, streakDays, mostFriends);
    }

       // -------------------------
    // BLOCKS
    // -------------------------
    @GetMapping("/blocks")
    public List<BlockItem> blocks(@RequestHeader(value = "X-Username", required = false) String username) {
        username = requireUsername(username);

        List<String> list = new ArrayList<>(store.getBlocked(username));
        list.sort(String::compareToIgnoreCase);

        List<BlockItem> out = new ArrayList<>();
        for (String b : list) {
            out.add(new BlockItem(b));
        }
        return out;
    }

    // -------------------------
    // PREFERENCES (Topics)
    // -------------------------
    @GetMapping("/preferences/topics")
    public PreferencesResponse topics(@RequestHeader(value = "X-Username", required = false) String username) {
        username = requireUsername(username);
        return preferencesService.getPreferences(username);
    }

    @PutMapping("/preferences/topics")
    public OkResponse saveTopics(
            @RequestBody SavePreferencesRequest req,
            @RequestHeader(value = "X-Username", required = false) String username
    ) {
        username = requireUsername(username);
        preferencesService.saveSelectedTopics(username, req.getSelectedTopics());
        return new OkResponse(true, "Saved");
    }

    @PostMapping("/preferences/topics/custom")
    public OkResponse addCustomTopic(
            @RequestBody AddCustomTopicRequest req,
            @RequestHeader(value = "X-Username", required = false) String username
    ) {
        username = requireUsername(username);
        preferencesService.addCustomTopic(username, req.getTopic());
        return new OkResponse(true, "Added");
    }

    // -------------------------
    // LOGOUT
    // -------------------------
    @PostMapping("/logout")
    public OkResponse logout() {
        return new OkResponse(true, "Logged out");
    }
}
