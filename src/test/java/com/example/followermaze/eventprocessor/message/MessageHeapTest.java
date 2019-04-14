package com.example.followermaze.eventprocessor.message;

import com.example.followermaze.eventprocessor.user.UserId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageHeapTest {
    private static Message message1 = new Message(1, MessageType.FOLLOW, new UserId(10), new UserId(20), "1|F|10|20");
    private static Message message2 = new Message(2, MessageType.UNFOLLOW, new UserId(10), new UserId(20), "2|U|10|20");
    private static Message message3 = new Message(3, MessageType.BROADCAST, new UserId(-1), new UserId(-1), "3|B");

    @BeforeEach
    void setUp() {
       MessageHeap.clearMessageHeap();
    }

    @Test
    void addMessage() {
        assertEquals(MessageHeap.getMessageHeapSize(), 0 , "MessageHeap should be empty");
        MessageHeap.addMessage(message1);
        assertEquals(MessageHeap.getMessageHeapSize(),1, "Message could not be added");
    }

    @Test
    void getNextOrderedMessage() {
        MessageHeap.addMessage(message1);
        MessageHeap.addMessage(message3);
        MessageHeap.addMessage(message2);

        assertEquals(MessageHeap.getNextOrderedMessage(), message1, "Message1 should be retrieved");
        assertEquals(MessageHeap.getNextOrderedMessage(), message2, "Message2 should be retrieved");
        assertEquals(MessageHeap.getNextOrderedMessage(), message3, "Message3 should be retrieved");
    }

    @Test
    void isEmpty() {
        assertEquals(MessageHeap.getMessageHeapSize(), 0 , "MessageHeap size should be 0");
        assertEquals(MessageHeap.isEmpty(), true, "MessageHeap should be empty");
        MessageHeap.addMessage(message1);
        assertEquals(MessageHeap.isEmpty(), false, "MessageHeap should not be empty");
    }

    @Test
    void clearMessageHeap() {
        MessageHeap.addMessage(message1);
        MessageHeap.addMessage(message2);
        assertEquals(MessageHeap.getMessageHeapSize(), 2, "MessageHeap should not be empty");

        MessageHeap.clearMessageHeap();
        assertEquals(MessageHeap.getMessageHeapSize(), 0, "MessageHeap should ne empty");
    }

    @Test
    void getMessageHeapSize() {
        assertEquals(MessageHeap.getMessageHeapSize(), 0, "MessageHeap size should be 0");

        MessageHeap.addMessage(message1);
        assertEquals(MessageHeap.getMessageHeapSize(), 1, "MessageHeap size should be 1");

        MessageHeap.addMessage(message2);
        assertEquals(MessageHeap.getMessageHeapSize(), 2, "MessageHeap size should be 2");

        MessageHeap.addMessage(message3);
        assertEquals(MessageHeap.getMessageHeapSize(), 3, "MessageHeap size should be 3");
    }
}