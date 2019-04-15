package com.example.followermaze.eventprocessor.user;

import com.example.followermaze.eventprocessor.message.Message;
import com.example.followermaze.eventprocessor.message.MessageType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserMessagesQueueTest {

    private final UserId user1 = new UserId(10);
    private final UserId user2 = new UserId(20);

    private final Message message1 = new Message(1, MessageType.FOLLOW, new UserId(10), new UserId(20), "1|F|10|20");
    private final Message message2 = new Message(2, MessageType.UNFOLLOW, new UserId(10), new UserId(20), "2|U|10|20");
    private final Message message3 = new Message(3, MessageType.BROADCAST, new UserId(-1), new UserId(-1), "3|B");
    private final UserMessagesQueue userMessagesQueue = UserMessagesQueue.getInstance();

    @BeforeEach
    void setUp(){
        userMessagesQueue.clearUserMessages();
        userMessagesQueue.validateUser(user1);
        userMessagesQueue.validateUser(user2);
    }

    @Test
    void insertMessage() {
        userMessagesQueue.insertMessage(user2, message1);

        assertEquals(userMessagesQueue.containsMessageForUser(user2), true, "Message does not inserted to the queue");
    }

    @Test
    void insertMessageToAll() {
        userMessagesQueue.insertMessageToAll(message3);

        assertEquals(userMessagesQueue.containsMessageForUser(user1), true, "Message does not inserted to User");
        assertEquals(userMessagesQueue.containsMessageForUser(user2), true, "Message does not inserted to User");
    }

    @Test
    void pollMessage() {
        userMessagesQueue.insertMessage(user1, message1);
        userMessagesQueue.insertMessage(user1, message2);
        userMessagesQueue.insertMessage(user1, message3);

        assertEquals(userMessagesQueue.pollMessage(user1), message1, "Message1 should be retrieved");
        assertEquals(userMessagesQueue.pollMessage(user1), message2, "Message2 should be retrieved");
        assertEquals(userMessagesQueue.pollMessage(user1), message3, "Message3 should be retrieved");
    }

    @Test
    void containsMessageForUser() {
        userMessagesQueue.insertMessage(user2, message1);
        assertEquals(userMessagesQueue.containsMessageForUser(user2), true, "Message queue for User is empty");

        userMessagesQueue.pollMessage(user2);
        assertEquals(userMessagesQueue.containsMessageForUser(user2), false, "Message queue for User should be empty");
    }

    @Test
    void validateUser() {
        userMessagesQueue.clearUserMessages();

        userMessagesQueue.insertMessage(user1, message1);
        assertNull(userMessagesQueue.pollMessage(user1), "User should be invalid");

        userMessagesQueue.validateUser(user1);
        userMessagesQueue.insertMessage(user1, message1);
        assertEquals(userMessagesQueue.pollMessage(user1), message1, "User should be valid");
    }

    @Test
    void invalidateUser() {
        userMessagesQueue.clearUserMessages();

        userMessagesQueue.validateUser(user1);
        userMessagesQueue.insertMessage(user1, message1);
        assertEquals(userMessagesQueue.pollMessage(user1), message1, "User should be valid");

        userMessagesQueue.invalidateUser(String.valueOf(user1.getKey()));
        userMessagesQueue.insertMessage(user1, message1);
        assertNull(userMessagesQueue.pollMessage(user1), "User should be invalid");


    }
}