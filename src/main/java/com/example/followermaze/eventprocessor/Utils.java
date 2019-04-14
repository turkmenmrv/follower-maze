package com.example.followermaze.eventprocessor;

public class Utils {
    public static int getEventSourcePort(){
        String eventListenerPort = System.getenv("eventListenerPort");
        if(eventListenerPort != null && !eventListenerPort.isEmpty()){
            return Integer.valueOf(eventListenerPort);
        }
        return 9090;
    }

    public static int getUserClientPort(){
        String clientListenerPort = System.getenv("clientListenerPort");
        if(clientListenerPort != null && !clientListenerPort.isEmpty()){
            return Integer.valueOf(clientListenerPort);
        }
        return 9099;
    }
}
