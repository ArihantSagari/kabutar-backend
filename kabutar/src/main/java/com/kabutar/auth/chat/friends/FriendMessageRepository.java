package com.kabutar.auth.chat.friends;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FriendMessageRepository extends CrudRepository<FriendMessage, String> {

    @Query("""
        SELECT m FROM FriendMessage m
        WHERE (m.fromUser = :u1 AND m.toUser = :u2)
           OR (m.fromUser = :u2 AND m.toUser = :u1)
        ORDER BY m.sentAt DESC
    """)
    List<FriendMessage> findConversation(@Param("u1") String u1,
                                         @Param("u2") String u2,
                                         Pageable pageable);
}
