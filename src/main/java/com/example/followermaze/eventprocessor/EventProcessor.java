package com.example.followermaze.eventprocessor;

import com.example.followermaze.eventprocessor.controller.EventSourceController;
import com.example.followermaze.eventprocessor.controller.UserClientController;

public class EventProcessor {
    private EventSourceController eventSourceController;
    private UserClientController userClientController;

    public EventProcessor(){
        eventSourceController = new EventSourceController();
        userClientController = new UserClientController();
    }

    public static void main(String[] args){
        System.out.println("Starting EventProcessor");
        EventProcessor eventProcessor = new EventProcessor();
        eventProcessor.userClientController.startController();
        eventProcessor.eventSourceController.startController();
    }
}
