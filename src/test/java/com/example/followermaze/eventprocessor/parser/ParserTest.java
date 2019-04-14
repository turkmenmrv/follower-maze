package com.example.followermaze.eventprocessor.parser;

import com.example.followermaze.eventprocessor.message.Message;
import com.example.followermaze.eventprocessor.message.MessageType;
import com.example.followermaze.eventprocessor.user.UserId;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {
    static IParser parser = new Parser();

    @Test
    void createMessage() {
        String seq1 = "666";
        String type1 = "F";
        String from1 = "60";
        String to1 = "50";
        String payload1 = seq1 + "|" + type1 + "|" + from1 + "|" + to1;

        String seq2 = "634";
        String type2= "S";
        String from2 = "32";
        String payload2 = seq2 + "|" + type2 + "|" + from2;

        String messageStr = payload1 + "\n" + payload2 + "\n";
        List<Message> messageList = parser.createMessage(messageStr);

        assertNotNull(messageList, "MessageList is NULL!");
        assertEquals(messageList.size(), 2, "MessageList size - expected: " + 2 + " actual: " + messageList.size());

        assertEquals(messageList.get(0).getPayload(), payload1, "Payload1 is wrong");
        assertEquals(messageList.get(1).getPayload(), payload2, "Payload2 is wrong");

        assertEquals(messageList.get(0).getFromUserId(), new UserId(Integer.valueOf(from1)), "FromUserId is wrong");
        assertEquals(messageList.get(0).getToUserId(), new UserId(Integer.valueOf(to1)), "ToUserId is wrong");
        assertEquals(messageList.get(0).getSequence() , Long.valueOf(seq1), "Sequence is wrong");

        assertEquals(messageList.get(0).getType(), MessageType.FOLLOW, "MessageType is wrong");
    }

    @Test
    void parseFollowMessage(){
        String seq = "666";
        String type = "F";
        String from = "60";
        String to = "50";
        String payload = seq + "|" + type + "|" + from + "|" + to;

        Message message = parser.createMessage(payload + "\n").get(0);

        assertEquals(message.getType(), MessageType.FOLLOW, "Message type is wrong");
        assertEquals(message.getPayload(), payload, "Payload is wrong");
        assertEquals(message.getSequence(), Long.valueOf(seq), "Sequence is wrong");
        assertEquals(message.getFromUserId(), new UserId(Integer.valueOf(from)), "FromUserId is wrong");
        assertEquals(message.getToUserId(), new UserId(Integer.valueOf(to)), "ToUserId is wrong");
    }

    @Test
    void parseUnfollowMessage(){
        String seq = "1";
        String type = "U";
        String from = "12";
        String to = "9";
        String payload = seq + "|" + type + "|" + from + "|" + to;

        Message message = parser.createMessage(payload + "\n").get(0);

        assertEquals(message.getType(), MessageType.UNFOLLOW, "Message type is wrong");
        assertEquals(message.getPayload(), payload, "Payload is wrong");
        assertEquals(message.getSequence(), Long.valueOf(seq), "Sequence is wrong");
        assertEquals(message.getFromUserId(), new UserId(Integer.valueOf(from)), "FromUserId is wrong");
        assertEquals(message.getToUserId(), new UserId(Integer.valueOf(to)), "ToUserId is wrong");
    }

    @Test
    void parseBroadcastMessage(){
        String seq = "542532";
        String type = "B";
        String payload = seq + "|" + type;

        Message message = parser.createMessage(payload + "\n").get(0);

        assertEquals(message.getType(), MessageType.BROADCAST, "Message type is wrong");
        assertEquals(message.getPayload(), payload, "Payload is wrong");
        assertEquals(message.getSequence(), Long.valueOf(seq), "Sequence is wrong");
        assertEquals(message.getFromUserId(), new UserId(Integer.valueOf(-1)), "FromUserId is wrong");
        assertEquals(message.getToUserId(), new UserId(Integer.valueOf(-1)), "ToUserId is wrong");
    }

    @Test
    void parsePrivateMessage(){
        String seq = "43";
        String type = "P";
        String from = "32";
        String to = "56";
        String payload = seq + "|" + type + "|" + from + "|" + to;

        Message message = parser.createMessage(payload + "\n").get(0);

        assertEquals(message.getType(), MessageType.PRIVATEMESSAGE, "Message type is wrong");
        assertEquals(message.getPayload(), payload, "Payload is wrong");
        assertEquals(message.getSequence() , Long.valueOf(seq), "Sequence is wrong");
        assertEquals(message.getFromUserId(), new UserId(Integer.valueOf(from)), "FromUserId is wrong");
        assertEquals(message.getToUserId(), new UserId(Integer.valueOf(to)), "ToUserId is wrong");
    }

    @Test
    void parseStatusMessage(){
        String seq = "634";
        String type = "S";
        String from = "32";
        String payload = seq + "|" + type + "|" + from;

        Message message = parser.createMessage(payload + "\n").get(0);

        assertEquals(message.getType(), MessageType.STATUSUPDATE, "Message type is wrong");
        assertEquals(message.getPayload(), payload, "Payload is wrong");
        assertEquals(message.getSequence(), Long.valueOf(seq), "Sequence is wrong");
        assertEquals(message.getFromUserId(), new UserId(Integer.valueOf(from)), "FromUserId is wrong");
        assertEquals(message.getToUserId(), new UserId(Integer.valueOf(-1)), "ToUserId is wrong");
    }

}