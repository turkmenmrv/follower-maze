package com.example.followermaze.eventprocessor.strategy;

import com.example.followermaze.eventprocessor.message.Message;
import com.example.followermaze.eventprocessor.user.UserMessagesQueue;

public class PrivateNotification implements NotificationStrategy {

    @Override
    public void createNotification(Message message) {
        UserMessagesQueue.getInstance().insertMessage(message.getToUserId(), message);
    }
}
