package com.example.followermaze.eventprocessor.message;

import java.util.PriorityQueue;

public class MessageHeap {

    private static PriorityQueue<Message> messageBuffer;

    static {
        messageBuffer = new PriorityQueue<Message>(new MessageComparator());
    }

    public static void addMessage(Message message){
        messageBuffer.add(message);
    }

    public static Message getNextOrderedMessage(){
        return messageBuffer.poll();
    }

    public static boolean isEmpty(){
        return messageBuffer.isEmpty();
    }

    public static void clearMessageHeap(){
        messageBuffer.clear();
    }

    public static int getMessageHeapSize(){
        return messageBuffer.size();
    }
}

class MessageComparator implements java.util.Comparator<Message> {

    @Override
    public int compare(final Message m1, final Message m2) {
        return Long.compare(m1.getSequence(), m2.getSequence());
    }
}