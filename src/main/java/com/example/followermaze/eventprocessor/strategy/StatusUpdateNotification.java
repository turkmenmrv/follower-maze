package com.example.followermaze.eventprocessor.strategy;

import com.example.followermaze.eventprocessor.message.Message;
import com.example.followermaze.eventprocessor.user.FollowerCache;
import com.example.followermaze.eventprocessor.user.UserId;
import com.example.followermaze.eventprocessor.user.UserMessagesQueue;

import java.util.List;

public class StatusUpdateNotification implements NotificationStrategy {

    private UserMessagesQueue userMessagesQueue = UserMessagesQueue.getInstance();

    @Override
    public void createNotification(Message message) {
        List<UserId> followers = FollowerCache.getInstance().getFollowers(message.getFromUserId());
        for(UserId id : followers){
            userMessagesQueue.insertMessage(id, message);
        }
    }
}
