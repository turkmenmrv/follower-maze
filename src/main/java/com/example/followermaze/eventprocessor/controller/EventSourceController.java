package com.example.followermaze.eventprocessor.controller;

import com.example.followermaze.eventprocessor.Utils;
import com.example.followermaze.eventprocessor.connection.EventSourceSocketServer;
import com.example.followermaze.eventprocessor.connection.ISocketServer;
import com.example.followermaze.eventprocessor.message.Message;
import com.example.followermaze.eventprocessor.message.MessageHeap;
import com.example.followermaze.eventprocessor.parser.IParser;
import com.example.followermaze.eventprocessor.parser.Parser;
import com.example.followermaze.eventprocessor.strategy.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class EventSourceController {
    private final IParser parser = new Parser();
    private final ISocketServer eventSocketServer = new EventSourceSocketServer();
    private final Logger logger = LoggerFactory.getLogger(EventSourceController.class);

    public void startController(){

        new Thread(()->{
            try {
                eventSocketServer.startListener(Utils.getEventSourcePort(), this::processMessage);
            } catch (IOException e) {
                logger.error("Error in starting Event Source listener",e);
            }
        }).start();
    }


    public void processMessage(String messageChunk) {

        List<Message> messageList = parser.createMessage(messageChunk);

        for(Message message : messageList){
            MessageHeap.addMessage(message);
        }

        while(!MessageHeap.isEmpty()){
            Message message = MessageHeap.getNextOrderedMessage();
            NotificationStrategy.getNotification(message.getType()).createNotification(message);
        }

    }


}
