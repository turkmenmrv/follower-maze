package com.example.followermaze.eventprocessor.strategy;

import com.example.followermaze.eventprocessor.message.Message;
import com.example.followermaze.eventprocessor.user.FollowerCache;
import com.example.followermaze.eventprocessor.user.UserId;
import com.example.followermaze.eventprocessor.user.UserMessages;

import java.util.List;

public class StatusUpdateNotification implements NotificationStrategy {

    @Override
    public void createNotification(Message message) {
        List<UserId> followers = FollowerCache.getFollowers(message.getFromUserId());
        for(UserId id : followers){
            UserMessages.insertMessage(id, message);
        }
    }
}
