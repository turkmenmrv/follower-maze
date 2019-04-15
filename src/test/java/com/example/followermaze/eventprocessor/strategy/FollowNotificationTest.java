package com.example.followermaze.eventprocessor.strategy;

import com.example.followermaze.eventprocessor.message.Message;
import com.example.followermaze.eventprocessor.message.MessageType;
import com.example.followermaze.eventprocessor.user.FollowerCache;
import com.example.followermaze.eventprocessor.user.UserId;
import com.example.followermaze.eventprocessor.user.UserMessagesQueue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FollowNotificationTest {
    private static final UserId user1 = new UserId(10);
    private static final UserId user2 = new UserId(20);
    private static final Message message = new Message(1, MessageType.FOLLOW, user1, user2, "1|F|10|20");

    @BeforeAll
    public static void setUp(){
        UserMessagesQueue.getInstance().clearUserMessages();
        FollowerCache.getInstance().clearFollowerCache();
        UserMessagesQueue.getInstance().validateUser(user1);
        UserMessagesQueue.getInstance().validateUser(user2);
    }

    @Test
    void createNotification() {
        NotificationStrategy followStrategy = NotificationStrategy.getNotification(message.getType());
        assertEquals(followStrategy.getClass(), FollowNotification.class, "Strategy should be Follow");
        followStrategy.createNotification(message);
        assertNull(UserMessagesQueue.getInstance().pollMessage(user1), "There should be no message for User1");
        assertEquals(UserMessagesQueue.getInstance().pollMessage(user2), message, "Message is not in the queue for user2");
        assertEquals(FollowerCache.getInstance().getFollowers(user2).contains(user1), true, "Follower cache is invalid");
    }
}