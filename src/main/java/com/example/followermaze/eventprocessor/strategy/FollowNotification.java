package com.example.followermaze.eventprocessor.strategy;

import com.example.followermaze.eventprocessor.message.Message;
import com.example.followermaze.eventprocessor.user.FollowerCache;
import com.example.followermaze.eventprocessor.user.UserMessages;

public class FollowNotification implements NotificationStrategy {

    @Override
    public void createNotification(Message message) {
        FollowerCache.followUser(message.getToUserId(), message.getFromUserId());
        UserMessages.insertMessage(message.getToUserId(), message);
    }
}
