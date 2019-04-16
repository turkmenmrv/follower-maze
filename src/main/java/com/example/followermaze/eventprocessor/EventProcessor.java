package com.example.followermaze.eventprocessor;

import com.example.followermaze.eventprocessor.connection.EventSourceSocketServer;
import com.example.followermaze.eventprocessor.connection.ISocketServer;
import com.example.followermaze.eventprocessor.controller.EventSourceController;
import com.example.followermaze.eventprocessor.controller.UserClientController;
import com.example.followermaze.eventprocessor.message.MessageHeap;
import com.example.followermaze.eventprocessor.parser.IParser;
import com.example.followermaze.eventprocessor.parser.Parser;
import com.example.followermaze.eventprocessor.user.UserMessagesQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventProcessor {
    private static final Logger logger = LoggerFactory.getLogger(EventProcessor.class);

    private final EventSourceController eventSourceController;
    private final UserClientController userClientController;
    private final IParser parser = new Parser();
    private final ISocketServer socketServer = new EventSourceSocketServer();

    public EventProcessor(){
        eventSourceController = new EventSourceController(parser,socketServer, MessageHeap.getInstance());
        userClientController = new UserClientController(UserMessagesQueue.getInstance());
    }

    public static void main(String[] args){
        logger.info("Starting EventProcessor!..");
        EventProcessor eventProcessor = new EventProcessor();
        eventProcessor.userClientController.startController();
        eventProcessor.eventSourceController.startController();
    }
}
