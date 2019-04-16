package com.example.followermaze.eventprocessor.connection;

import com.example.followermaze.eventprocessor.Utils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class UserClientSocketServer implements ISocketServer {
    private final ExecutorService executorService = Executors.newFixedThreadPool(Utils.getConcurrencyLevel());

    @Override
    public void startListener(int port, Consumer<String> consumer) throws IOException {
        try (ServerSocket ss = new ServerSocket(port)) {
            while(true){

                Socket socket = ss.accept();
                ISocketHandler userReader = new UserClientHandler();
                userReader.setConsumer(consumer);
                userReader.setSocket(socket);
                executorService.submit(()->{
                    userReader.handleIO();});
            }
        }
        finally {
            executorService.shutdown();
        }
    }
}
