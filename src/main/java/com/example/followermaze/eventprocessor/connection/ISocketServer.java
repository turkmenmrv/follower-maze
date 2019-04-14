package com.example.followermaze.eventprocessor.connection;

import java.io.IOException;
import java.util.function.Consumer;

public interface ISocketServer {
    void startListener (int port, Consumer<String> handler) throws IOException;
}
