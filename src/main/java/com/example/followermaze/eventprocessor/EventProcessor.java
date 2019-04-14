package com.example.followermaze.eventprocessor;

import com.example.followermaze.eventprocessor.controller.EventSourceController;
import com.example.followermaze.eventprocessor.controller.UserClientController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventProcessor {
    private EventSourceController eventSourceController;
    private UserClientController userClientController;
    private static final Logger logger = LoggerFactory.getLogger(EventProcessor.class);

    public EventProcessor(){
        eventSourceController = new EventSourceController();
        userClientController = new UserClientController();
    }

    public static void main(String[] args){
        logger.info("Starting EventProcessor...");
        EventProcessor eventProcessor = new EventProcessor();
        eventProcessor.userClientController.startController();
        eventProcessor.eventSourceController.startController();
    }
}
