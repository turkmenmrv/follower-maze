package com.example.followermaze.eventprocessor.strategy;

import com.example.followermaze.eventprocessor.message.Message;
import com.example.followermaze.eventprocessor.user.FollowerCache;

public class UnfollowNotification implements NotificationStrategy {

    @Override
    public void createNotification(final Message message) {
        FollowerCache.unfollowUser(message.getToUserId(), message.getFromUserId());
    }
}
