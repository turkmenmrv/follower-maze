package com.example.followermaze.eventprocessor.user;


import com.example.followermaze.eventprocessor.message.Message;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class UserMessages {
    private static ConcurrentMap<UserId, Queue<Message>> userMessageQueue;

    static{
        userMessageQueue = new ConcurrentHashMap<>();
    }

    public static void insertMessage(UserId userId, Message message){
        if(userMessageQueue.containsKey(userId)){
            Queue<Message> messageQueue = userMessageQueue.get(userId);
            messageQueue.add(message);
        }
        //TODO: log
//        else{
//            Queue<message> messageQueue = new LinkedList<>();
//            messageQueue.add(message);
//            userMessageQueue.put(userId, messageQueue);
//        }
    }

    public static void insertMessageToAll(Message message){
        for(ConcurrentMap.Entry<UserId, Queue<Message>> entity : userMessageQueue.entrySet()){
            entity.getValue().add(message);
        }
    }

    public static Message pollMessage(UserId userId){
        if(userMessageQueue.containsKey(userId))
            return userMessageQueue.get(userId).poll();
        return null;
    }

    public static boolean containsMessageForUser(String userId){
        return containsMessageForUser(new UserId(Integer.valueOf(userId)));
    }

    public static boolean containsMessageForUser(UserId userId){
        return (userMessageQueue.containsKey(userId) && userMessageQueue.get(userId).size()>0);
    }

    public static void validateUser(UserId userId){
        Queue<Message> messageQueue = new LinkedList<>();
        userMessageQueue.put(userId, messageQueue);
    }


    public static void invalidateUser(String userId){
        UserId id = new UserId(Integer.valueOf(userId));
        if(id.getKey() > 0 && userMessageQueue.containsKey(userId)){
            userMessageQueue.remove(userId);
        }
    }

    public static void clearUserMessages(){
        userMessageQueue.clear();
    }
}
