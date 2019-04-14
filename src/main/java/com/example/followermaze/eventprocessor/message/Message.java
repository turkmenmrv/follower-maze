package com.example.followermaze.eventprocessor.message;

import com.example.followermaze.eventprocessor.user.UserId;

public class Message {
    private long sequence;
    private MessageType type;
    private UserId fromUserId;
    private UserId toUserId;
    private String payload;

    public Message(long sequence, MessageType type, UserId fromUserId, UserId toUserId, String payload){
        this.sequence = sequence;
        this.type = type;
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.payload = payload;
    }

    public long getSequence() {
        return sequence;
    }

    public MessageType getType() {
        return type;
    }

    public UserId getFromUserId() {
        return fromUserId;
    }

    public UserId getToUserId() {
        return toUserId;
    }

    public String getPayload() {
        return payload;
    }
}
