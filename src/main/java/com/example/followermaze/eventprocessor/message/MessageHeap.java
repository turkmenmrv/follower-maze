package com.example.followermaze.eventprocessor.message;

import java.util.PriorityQueue;

public class MessageHeap {

    private final PriorityQueue<Message> messageBuffer =new PriorityQueue<Message>(new MessageComparator());
    private static MessageHeap instance = new MessageHeap();

    private MessageHeap(){}

    public static MessageHeap getInstance(){
        return instance;
    }

    public void addMessage(Message message){
        messageBuffer.add(message);
    }

    public Message getNextOrderedMessage(){
        return messageBuffer.poll();
    }

    public boolean isEmpty(){
        return messageBuffer.isEmpty();
    }

    public void clearMessageHeap(){
        messageBuffer.clear();
    }

    public int getMessageHeapSize(){
        return messageBuffer.size();
    }
}

class MessageComparator implements java.util.Comparator<Message> {

    @Override
    public int compare(Message m1, Message m2) {
        return Long.compare(m1.getSequence(), m2.getSequence());
    }
}