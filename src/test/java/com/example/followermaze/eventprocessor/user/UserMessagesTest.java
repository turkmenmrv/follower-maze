package com.example.followermaze.eventprocessor.user;

import com.example.followermaze.eventprocessor.message.Message;
import com.example.followermaze.eventprocessor.message.MessageType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserMessagesTest {

    private static UserId user1 = new UserId(10);
    private static UserId user2 = new UserId(20);

    private static Message message1 = new Message(1, MessageType.FOLLOW, new UserId(10), new UserId(20), "1|F|10|20");
    private static Message message2 = new Message(2, MessageType.UNFOLLOW, new UserId(10), new UserId(20), "2|U|10|20");
    private static Message message3 = new Message(3, MessageType.BROADCAST, new UserId(-1), new UserId(-1), "3|B");


    @BeforeEach
    void setUp(){
        UserMessages.clearUserMessages();
        UserMessages.validateUser(user1);
        UserMessages.validateUser(user2);
    }

    @Test
    void insertMessage() {
        UserMessages.insertMessage(user2, message1);

        assertEquals(UserMessages.containsMessageForUser(user2), true, "Message does not inserted to the queue");
    }

    @Test
    void insertMessageToAll() {
        UserMessages.insertMessageToAll(message3);

        assertEquals(UserMessages.containsMessageForUser(user1), true, "Message does not inserted to User");
        assertEquals(UserMessages.containsMessageForUser(user2), true, "Message does not inserted to User");
    }

    @Test
    void pollMessage() {
        UserMessages.insertMessage(user1, message1);
        UserMessages.insertMessage(user1, message2);
        UserMessages.insertMessage(user1, message3);

        assertEquals(UserMessages.pollMessage(user1), message1, "Message1 should be retrieved");
        assertEquals(UserMessages.pollMessage(user1), message2, "Message2 should be retrieved");
        assertEquals(UserMessages.pollMessage(user1), message3, "Message3 should be retrieved");
    }

    @Test
    void containsMessageForUser() {
        UserMessages.insertMessage(user2, message1);
        assertEquals(UserMessages.containsMessageForUser(user2), true, "Message queue for User is empty");

        UserMessages.pollMessage(user2);
        assertEquals(UserMessages.containsMessageForUser(user2), false, "Message queue for User should be empty");
    }

    @Test
    void validateUser() {
        UserMessages.clearUserMessages();

        UserMessages.insertMessage(user1, message1);
        assertNull(UserMessages.pollMessage(user1), "User should be invalid");

        UserMessages.validateUser(user1);
        UserMessages.insertMessage(user1, message1);
        assertEquals(UserMessages.pollMessage(user1), message1, "User should be valid");
    }

    @Test
    void invalidateUser() {
        UserMessages.clearUserMessages();

        UserMessages.validateUser(user1);
        UserMessages.insertMessage(user1, message1);
        assertEquals(UserMessages.pollMessage(user1), message1, "User should be valid");

        UserMessages.invalidateUser(String.valueOf(user1.getKey()));
        UserMessages.insertMessage(user1, message1);
        assertNull(UserMessages.pollMessage(user1), "User should be invalid");


    }
}