package com.example.followermaze.eventprocessor.strategy;

import com.example.followermaze.eventprocessor.message.Message;
import com.example.followermaze.eventprocessor.user.UserMessages;

public class BroadcastNotification implements NotificationStrategy {

    @Override
    public void createNotification(Message message) {
        UserMessages.insertMessageToAll(message);
    }
}
