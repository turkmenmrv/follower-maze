package com.example.followermaze.eventprocessor.message;

import com.example.followermaze.eventprocessor.user.UserId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageHeapTest {
    private static final Message message1 = new Message(1, MessageType.FOLLOW, new UserId(10), new UserId(20), "1|F|10|20");
    private static final Message message2 = new Message(2, MessageType.UNFOLLOW, new UserId(10), new UserId(20), "2|U|10|20");
    private static final Message message3 = new Message(3, MessageType.BROADCAST, new UserId(-1), new UserId(-1), "3|B");

    @BeforeEach
    void setUp() {
       MessageHeap.getInstance().clearMessageHeap();
    }

    @Test
    void addMessage() {
        assertEquals(MessageHeap.getInstance().getMessageHeapSize(), 0 , "MessageHeap should be empty");
        MessageHeap.getInstance().addMessage(message1);
        assertEquals(MessageHeap.getInstance().getMessageHeapSize(),1, "Message could not be added");
    }

    @Test
    void getNextOrderedMessage() {
        MessageHeap.getInstance().addMessage(message1);
        MessageHeap.getInstance().addMessage(message3);
        MessageHeap.getInstance().addMessage(message2);

        assertEquals(MessageHeap.getInstance().getNextOrderedMessage(), message1, "Message1 should be retrieved");
        assertEquals(MessageHeap.getInstance().getNextOrderedMessage(), message2, "Message2 should be retrieved");
        assertEquals(MessageHeap.getInstance().getNextOrderedMessage(), message3, "Message3 should be retrieved");
    }

    @Test
    void isEmpty() {
        assertEquals(MessageHeap.getInstance().getMessageHeapSize(), 0 , "MessageHeap size should be 0");
        assertEquals(MessageHeap.getInstance().isEmpty(), true, "MessageHeap should be empty");
        MessageHeap.getInstance().addMessage(message1);
        assertEquals(MessageHeap.getInstance().isEmpty(), false, "MessageHeap should not be empty");
    }

    @Test
    void clearMessageHeap() {
        MessageHeap.getInstance().addMessage(message1);
        MessageHeap.getInstance().addMessage(message2);
        assertEquals(MessageHeap.getInstance().getMessageHeapSize(), 2, "MessageHeap should not be empty");

        MessageHeap.getInstance().clearMessageHeap();
        assertEquals(MessageHeap.getInstance().getMessageHeapSize(), 0, "MessageHeap should ne empty");
    }

    @Test
    void getMessageHeapSize() {
        assertEquals(MessageHeap.getInstance().getMessageHeapSize(), 0, "MessageHeap size should be 0");

        MessageHeap.getInstance().addMessage(message1);
        assertEquals(MessageHeap.getInstance().getMessageHeapSize(), 1, "MessageHeap size should be 1");

        MessageHeap.getInstance().addMessage(message2);
        assertEquals(MessageHeap.getInstance().getMessageHeapSize(), 2, "MessageHeap size should be 2");

        MessageHeap.getInstance().addMessage(message3);
        assertEquals(MessageHeap.getInstance().getMessageHeapSize(), 3, "MessageHeap size should be 3");
    }
}