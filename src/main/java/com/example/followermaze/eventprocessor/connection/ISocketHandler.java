package com.example.followermaze.eventprocessor.connection;


import java.net.Socket;
import java.util.function.Consumer;

public interface ISocketHandler {
    void handleIO();
    Consumer<String> getConsumer();
    void setConsumer(Consumer<String> consumer);
    void setSocket(Socket socket);
}
