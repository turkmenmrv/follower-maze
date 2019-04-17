package com.example.followermaze.eventprocessor.user;


import com.example.followermaze.eventprocessor.message.Message;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class UserMessagesQueue {
    private final ConcurrentMap<UserId, Queue<Message>> messageQueue = new ConcurrentHashMap<>();
    private static final UserMessagesQueue instance = new UserMessagesQueue();

    private UserMessagesQueue(){}

    public static UserMessagesQueue getInstance(){
        return instance;
    }

    public void insertMessage(UserId userId, Message message) {
        if (messageQueue.containsKey(userId)) {
            Queue<Message> queue = messageQueue.get(userId);
            queue.add(message);
        }
        //else: ignore message
    }

    public void insertMessageToAll(Message message){
        for(ConcurrentMap.Entry<UserId, Queue<Message>> entity : messageQueue.entrySet()){
            entity.getValue().add(message);
        }
    }

    public Message pollMessage(UserId userId){
        if(messageQueue.containsKey(userId))
            return messageQueue.get(userId).poll();
        return null;
    }

    public boolean containsMessageForUser(String userId){
        return containsMessageForUser(new UserId(Integer.valueOf(userId)));
    }

    public boolean containsMessageForUser(UserId userId){
        return (messageQueue.containsKey(userId) && messageQueue.get(userId).size()>0);
    }

    public void validateUser(UserId userId){
        Queue<Message> queue = new LinkedList<>();
        messageQueue.put(userId, queue);
    }


    public void invalidateUser(String userIdstr){
        UserId userId = new UserId(Integer.valueOf(userIdstr));
        if(userId.getKey() > 0 && messageQueue.containsKey(userId)){
            messageQueue.remove(userId);
        }
    }

    public void clearUserMessages(){
        messageQueue.clear();
    }
}
