package com.example.followermaze.eventprocessor.connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class EventSourceSocketServer implements ISocketServer {
    @Override
    public void startListener(final int port, final Consumer<String> consumer) throws IOException {
        try (ServerSocket ss = new ServerSocket(port)) {
            while(true) {
                Socket socket = ss.accept();
                ISocketHandler eventReader = new EventSourceHandler();
                eventReader.setConsumer(consumer);

                eventReader.setSocket(socket);
                System.out.println("accepted event source");
                eventReader.handleIO();
            }
        }
    }
}
