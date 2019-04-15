package com.example.followermaze.eventprocessor.strategy;

import com.example.followermaze.eventprocessor.message.Message;
import com.example.followermaze.eventprocessor.message.MessageType;
import com.example.followermaze.eventprocessor.user.FollowerCache;
import com.example.followermaze.eventprocessor.user.UserId;
import com.example.followermaze.eventprocessor.user.UserMessages;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrivateNotificationTest {
    private static final UserId user1 = new UserId(10);
    private static final UserId user2 = new UserId(20);
    private static final Message message = new Message(1, MessageType.PRIVATEMESSAGE, user1, user2, "1|P|10|20");

    @BeforeAll
    public static void setUp(){
        UserMessages.clearUserMessages();
        FollowerCache.clearFollowerCache();
        UserMessages.validateUser(user1);
        UserMessages.validateUser(user2);
    }

    @Test
    void createNotification() {
        NotificationStrategy privateStrategy = NotificationStrategy.getNotification(message.getType());
        assertEquals(privateStrategy.getClass(), PrivateNotification.class, "Strategy should be Private");
        privateStrategy.createNotification(message);
        assertNull(UserMessages.pollMessage(user1), "There should be no message for User1");
        assertEquals(UserMessages.pollMessage(user2), message, "Message is not in the queue for user2");
    }
}