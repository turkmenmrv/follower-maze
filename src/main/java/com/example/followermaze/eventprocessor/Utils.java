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

    public static int getMaxEventSourceBatchSize(){
        String maxEventSourceBatchSize = System.getenv("maxEventSourceBatchSize");
        if(maxEventSourceBatchSize != null && !maxEventSourceBatchSize.isEmpty()){
            return Integer.valueOf(maxEventSourceBatchSize);
        }
        return 100;
    }

    public static int getReadBufferSize(){
        String readBufferSize = System.getenv("readBufferSize");
        if(readBufferSize != null && !readBufferSize.isEmpty()){
            return Integer.valueOf(readBufferSize);
        }
        return 3000;
    }

    public static int getConcurrencyLevel(){
        String concurrencyLevel = System.getenv("concurrencyLevel");
        if(concurrencyLevel != null && !concurrencyLevel.isEmpty()){
            return Integer.valueOf(concurrencyLevel);
        }
        return 100;
    }
}
