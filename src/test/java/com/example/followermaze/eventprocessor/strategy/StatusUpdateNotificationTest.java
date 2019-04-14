package com.example.followermaze.eventprocessor.strategy;

import com.example.followermaze.eventprocessor.message.Message;
import com.example.followermaze.eventprocessor.message.MessageType;
import com.example.followermaze.eventprocessor.user.FollowerCache;
import com.example.followermaze.eventprocessor.user.UserId;
import com.example.followermaze.eventprocessor.user.UserMessages;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatusUpdateNotificationTest {
    private static UserId user1 = new UserId(10);
    private static UserId user2 = new UserId(20);
    private static Message message1 = new Message(1, MessageType.FOLLOW, user1, user2, "1|F|10|20");
    private static Message message2 = new Message(1, MessageType.STATUSUPDATE, user2, new UserId(-1), "2|S|20");

    @BeforeAll
    public static void setUp(){
        UserMessages.clearUserMessages();
        FollowerCache.clearFollowerCache();
        UserMessages.validateUser(user1);
        UserMessages.validateUser(user2);
        NotificationStrategy followNotification = NotificationStrategy.getNotification(message1.getType());
        followNotification.createNotification(message1);
    }

    @Test
    void createNotification() {
        NotificationStrategy statusUpdateNotification = NotificationStrategy.getNotification(message2.getType());
        assertEquals(statusUpdateNotification.getClass(), StatusUpdateNotification.class, "Strategy should be StatusUpdate");
        statusUpdateNotification.createNotification(message2);
        assertEquals(UserMessages.pollMessage(user1), message2,"There should be no message for User1");
    }
}