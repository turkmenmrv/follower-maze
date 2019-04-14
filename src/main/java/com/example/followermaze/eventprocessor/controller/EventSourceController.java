package com.example.followermaze.eventprocessor.controller;

import com.example.followermaze.eventprocessor.Utils;
import com.example.followermaze.eventprocessor.connection.EventSourceSocketServer;
import com.example.followermaze.eventprocessor.connection.ISocketServer;
import com.example.followermaze.eventprocessor.message.Message;
import com.example.followermaze.eventprocessor.message.MessageHeap;
import com.example.followermaze.eventprocessor.parser.IParser;
import com.example.followermaze.eventprocessor.parser.Parser;
import com.example.followermaze.eventprocessor.strategy.*;

import java.io.IOException;
import java.util.List;

public class EventSourceController {
    private IParser parser = new Parser();
    private ISocketServer eventSocketServer = new EventSourceSocketServer();

    public void startController(){

        new Thread(()->{
            try {
                eventSocketServer.startListener(Utils.getEventSourcePort(), this::processMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
//        try {
//            TimeUnit.SECONDS.sleep(20);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
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
