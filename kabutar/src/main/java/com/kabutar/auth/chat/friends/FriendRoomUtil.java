package com.kabutar.auth.chat.friends;

public class FriendRoomUtil {
    public static String friendRoomId(String a, String b) {
        String x = a.toLowerCase();
        String y = b.toLowerCase();
        if (x.compareTo(y) < 0) return "friend:" + x + ":" + y;
        return "friend:" + y + ":" + x;
    }
}
