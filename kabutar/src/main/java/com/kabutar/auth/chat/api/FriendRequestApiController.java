//package com.kabutar.auth.chat.api;
//
//import com.kabutar.auth.chat.api.dto.*;
//import com.kabutar.auth.chat.friends.FriendRequest;
//import com.kabutar.auth.chat.store.UserDataStore;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RestController
//@RequestMapping("/api/friend-requests")
//public class FriendRequestApiController {
//
//    private final UserDataStore store;
//
//    public FriendRequestApiController(UserDataStore store) {
//        this.store = store;
//    }
//
//    private String requireUsername(String username) {
//        if (username == null || username.isBlank()) {
//            throw new RuntimeException("X-Username missing");
//        }
//        return username.trim();
//    }
//
//    @PostMapping("/send")
//    public FriendRequestResponse send(
//            @RequestBody SendFriendRequestBody body,
//            @RequestHeader(value = "X-Username", required = false) String username
//    ) {
//        username = requireUsername(username);
//
//        FriendRequest req = store.createFriendRequest(username, body.getTo());
//
//        return new FriendRequestResponse(
//                req.getId(),
//                req.getFromUser(),
//                req.getToUser(),
//                req.getStatus(),
//                req.getCreatedAt()
//        );
//    }
//
//    @GetMapping("/incoming")
//    public List<FriendRequestResponse> incoming(
//            @RequestHeader(value = "X-Username", required = false) String username
//    ) {
//        username = requireUsername(username);
//
//        return store.getIncomingRequests(username)
//                .stream()
//                .map(r -> new FriendRequestResponse(
//                        r.getId(),
//                        r.getFromUser(),
//                        r.getToUser(),
//                        r.getStatus(),
//                        r.getCreatedAt()
//                ))
//                .collect(Collectors.toList());
//    }
//
//    @PostMapping("/accept")
//    public OkResponse accept(
//            @RequestBody RequestActionBody body,
//            @RequestHeader(value = "X-Username", required = false) String username
//    ) {
//        username = requireUsername(username);
//
//        store.acceptRequest(body.getRequestId(), username);
//        return new OkResponse(true, "Accepted");
//    }
//
//    @PostMapping("/reject")
//    public OkResponse reject(
//            @RequestBody RequestActionBody body,
//            @RequestHeader(value = "X-Username", required = false) String username
//    ) {
//        username = requireUsername(username);
//
//        store.rejectRequest(body.getRequestId(), username);
//        return new OkResponse(true, "Rejected");
//    }
//}
