package com.example.followermaze.eventprocessor.parser;

import com.example.followermaze.eventprocessor.message.Message;
import com.example.followermaze.eventprocessor.message.MessageType;
import com.example.followermaze.eventprocessor.user.UserId;

import java.util.ArrayList;
import java.util.List;

public class Parser implements IParser{
    @Override

    public List<Message> createMessage(String messageString){
        List<Message> messageList = new ArrayList<>();
        String[] strList = messageString.split("\n");

        for(String messageStr : strList) {
            String[] content = messageStr.split("\\|");
            long sequence = Long.valueOf(content[0]);
            MessageType messageType;
            int fromUserId = -1;
            int toUserId = -1;

            switch (content[1]) {
                case "F":
                    messageType = MessageType.FOLLOW;
                    fromUserId = Integer.valueOf(content[2]);
                    toUserId = Integer.valueOf(content[3]);
                    break;
                case "U":
                    messageType = MessageType.UNFOLLOW;
                    fromUserId = Integer.valueOf(content[2]);
                    toUserId = Integer.valueOf(content[3]);
                    break;
                case "B":
                    messageType = MessageType.BROADCAST;
                    break;
                case "P":
                    messageType = MessageType.PRIVATEMESSAGE;
                    fromUserId = Integer.valueOf(content[2]);
                    toUserId = Integer.valueOf(content[3]);
                    break;
                case "S":
                    messageType = MessageType.STATUSUPDATE;
                    fromUserId = Integer.valueOf(content[2]);
                    break;
                default:
                    messageType = MessageType.INVALID;
                    break;
            }

            messageList.add(new Message(sequence, messageType, new UserId(fromUserId), new UserId(toUserId), messageStr));
        }

        return messageList;
    }

}
