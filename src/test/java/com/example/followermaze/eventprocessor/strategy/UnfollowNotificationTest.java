package com.example.followermaze.eventprocessor.strategy;

import com.example.followermaze.eventprocessor.message.Message;
import com.example.followermaze.eventprocessor.message.MessageType;
import com.example.followermaze.eventprocessor.user.FollowerCache;
import com.example.followermaze.eventprocessor.user.UserId;
import com.example.followermaze.eventprocessor.user.UserMessagesQueue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnfollowNotificationTest {
    private static final UserId user1 = new UserId(10);
    private static final UserId user2 = new UserId(20);
    private static final Message message1 = new Message(1, MessageType.FOLLOW, user1, user2, "1|F|10|20");
    private static final Message message2 = new Message(1, MessageType.UNFOLLOW, user1, user2, "2|U|10|20");

    @BeforeAll
    public static void setUp(){
        UserMessagesQueue.getInstance().clearUserMessages();
        FollowerCache.getInstance().clearFollowerCache();
        UserMessagesQueue.getInstance().validateUser(user1);
        UserMessagesQueue.getInstance().validateUser(user2);
        NotificationStrategy followNotification = NotificationStrategy.getNotification(message1.getType());
        followNotification.createNotification(message1);
    }
    @Test
    void createNotification() {
        assertEquals(UserMessagesQueue.getInstance().pollMessage(user2), message1, "Message is not in the queue for user2");
        NotificationStrategy unfollowStrategy = NotificationStrategy.getNotification(message2.getType());
        assertEquals(unfollowStrategy.getClass(), UnfollowNotification.class, "Strategy should be Unfollow");
        unfollowStrategy.createNotification(message2);
        assertNull(UserMessagesQueue.getInstance().pollMessage(user1), "There should be no message for User1");
        assertNull(UserMessagesQueue.getInstance().pollMessage(user2), "There should be no message for User2");

        assertEquals(FollowerCache.getInstance().getFollowers(user2).isEmpty(), true, "Follower cache is invalid");
    }
}