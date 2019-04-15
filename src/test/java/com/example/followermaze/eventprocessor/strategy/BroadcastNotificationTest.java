package com.example.followermaze.eventprocessor.strategy;

import com.example.followermaze.eventprocessor.message.Message;
import com.example.followermaze.eventprocessor.message.MessageType;
import com.example.followermaze.eventprocessor.user.FollowerCache;
import com.example.followermaze.eventprocessor.user.UserId;
import com.example.followermaze.eventprocessor.user.UserMessages;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BroadcastNotificationTest {
    private static final UserId user1 = new UserId(10);
    private static final UserId user2 = new UserId(20);
    private static final Message message = new Message(3, MessageType.BROADCAST, new UserId(-1), new UserId(-1), "3|B");

    @BeforeAll
    public static void setUp(){
        UserMessages.clearUserMessages();
        FollowerCache.clearFollowerCache();
        UserMessages.validateUser(user1);
        UserMessages.validateUser(user2);
    }

    @Test
    void createNotification() {
        NotificationStrategy broadcastStrategy = NotificationStrategy.getNotification(message.getType());
        assertEquals(broadcastStrategy.getClass(), BroadcastNotification.class, "Strategy should be Broadcast");
        broadcastStrategy.createNotification(message);
        assertEquals(UserMessages.pollMessage(user1), message, "Message is not in the queue for user1");
        assertEquals(UserMessages.pollMessage(user2), message, "Message is not in the queue for user2");
    }
}