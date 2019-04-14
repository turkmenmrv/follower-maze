package com.example.followermaze.eventprocessor.strategy;

import com.example.followermaze.eventprocessor.message.Message;
import com.example.followermaze.eventprocessor.message.MessageType;

import static com.example.followermaze.eventprocessor.message.MessageType.*;

public interface NotificationStrategy {
    NotificationStrategy followNtf = new FollowNotification();
    NotificationStrategy unfollowNtf = new UnfollowNotification();
    NotificationStrategy broadcastNtf = new BroadcastNotification();
    NotificationStrategy privateNtf = new PrivateNotification();
    NotificationStrategy statusNtf = new StatusUpdateNotification();

    void createNotification(Message message);

    static NotificationStrategy getNotification(MessageType messageType){
        if(messageType == FOLLOW)
                return followNtf;
        if(messageType == UNFOLLOW)
            return  unfollowNtf;
        if(messageType == BROADCAST)
                return broadcastNtf;
        if(messageType == PRIVATEMESSAGE)
                return privateNtf;
        if(messageType == STATUSUPDATE)
            return statusNtf;
        throw new RuntimeException("Unexcpexted MessageType ["+messageType+"]");
    }
}
