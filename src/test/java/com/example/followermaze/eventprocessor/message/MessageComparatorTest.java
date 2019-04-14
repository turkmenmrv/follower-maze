package com.example.followermaze.eventprocessor.message;

import com.example.followermaze.eventprocessor.user.UserId;
import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

class MessageComparatorTest {

    @Test
    void compare() {
        Message message1_seq1 = new Message(1, MessageType.FOLLOW, new UserId(10), new UserId(20), "1|F|10|20");
        Message message2_seq1 = new Message(1, MessageType.UNFOLLOW, new UserId(10), new UserId(20), "2|U|10|20");
        Message message3_seq2 = new Message(2, MessageType.BROADCAST, new UserId(-1), new UserId(-1), "3|B");

        Comparator cmp = new MessageComparator();

        assertEquals(cmp.compare(message1_seq1, message3_seq2), -1, "Compare return value should be negative");
        assertEquals(cmp.compare(message3_seq2, message1_seq1), 1, "Compare return value should be positive");
        assertEquals(cmp.compare(message1_seq1, message2_seq1), 0, "Compare return value should be 0");
    }
}