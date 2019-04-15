package com.example.followermaze.eventprocessor.strategy;

import com.example.followermaze.eventprocessor.message.Message;
import com.example.followermaze.eventprocessor.user.FollowerCache;
import com.example.followermaze.eventprocessor.user.UserMessagesQueue;

public class FollowNotification implements NotificationStrategy {

    @Override
    public void createNotification(Message message) {
        FollowerCache.getInstance().followUser(message.getToUserId(), message.getFromUserId());
        UserMessagesQueue.getInstance().insertMessage(message.getToUserId(), message);
    }
}
