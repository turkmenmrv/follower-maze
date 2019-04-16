package com.example.followermaze.eventprocessor.controller;

import com.example.followermaze.eventprocessor.Utils;
import com.example.followermaze.eventprocessor.connection.ISocketServer;
import com.example.followermaze.eventprocessor.message.Message;
import com.example.followermaze.eventprocessor.message.MessageHeap;
import com.example.followermaze.eventprocessor.parser.IParser;
import com.example.followermaze.eventprocessor.strategy.NotificationStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class EventSourceController {
    private final IParser parser;
    private final ISocketServer eventSocketServer;
    private MessageHeap messageHeap;
    private final Logger logger = LoggerFactory.getLogger(EventSourceController.class);

    public EventSourceController(IParser parser,ISocketServer socketServer,MessageHeap messageHeap){

        this.parser = parser;
        this.eventSocketServer = socketServer;
        this.messageHeap = messageHeap;
    }

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

        if(messageChunk.length() > 0){
            List<Message> messageList = parser.createMessage(messageChunk);

            for(Message message : messageList){
                messageHeap.addMessage(message);
            }

            while (messageHeap.isSafeToPoll()) {
                Message message = messageHeap.getNextOrderedMessage();
                NotificationStrategy.getNotification(message.getType()).createNotification(message);
            }
        }
        else {
            while (!MessageHeap.getInstance().isEmpty()) {
                Message message = MessageHeap.getInstance().getNextOrderedMessage();
                NotificationStrategy.getNotification(message.getType()).createNotification(message);
            }
        }
    }


}
