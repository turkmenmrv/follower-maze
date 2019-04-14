package com.example.followermaze.eventprocessor.connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class UserClientSocketServer implements ISocketServer {
    ExecutorService executorService = Executors.newCachedThreadPool();

    @Override
    public void startListener(final int port, final Consumer<String> consumer) throws IOException {
        try (ServerSocket ss = new ServerSocket(port)) {
            while(true){

                Socket socket = ss.accept();
                ISocketHandler userReader = new UserClientHandler();
                userReader.setConsumer(consumer);
                userReader.setSocket(socket);
//                UserClientHandler reader = new UserClientHandler(socket);
//                    reader.handleIO();
                executorService.submit(()->{
                    userReader.handleIO();});
            }
        }
    }
}
