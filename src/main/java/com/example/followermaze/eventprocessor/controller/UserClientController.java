package com.example.followermaze.eventprocessor.controller;

import com.example.followermaze.eventprocessor.Utils;
import com.example.followermaze.eventprocessor.connection.ISocketHandler;
import com.example.followermaze.eventprocessor.connection.ISocketServer;
import com.example.followermaze.eventprocessor.connection.UserClientSocketServer;
import com.example.followermaze.eventprocessor.user.UserId;
import com.example.followermaze.eventprocessor.user.UserMessages;

import java.io.IOException;

public class UserClientController {
    private final ISocketServer userSocketServer = new UserClientSocketServer();

    public void startController() {
        new Thread(()->{
            try {
                userSocketServer.startListener(Utils.getUserClientPort(), this::processUser);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void processUser(String id){
        UserId userId = new UserId(Integer.valueOf(id));
        UserMessages.validateUser(userId);
    }
}
