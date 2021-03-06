package com.example.followermaze.eventprocessor.connection;

import com.example.followermaze.eventprocessor.message.Message;
import com.example.followermaze.eventprocessor.user.UserId;
import com.example.followermaze.eventprocessor.user.UserMessagesQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.function.Consumer;

public class UserClientHandler implements ISocketHandler {
    private Socket socket;
    private Consumer<String> consumer;
    private final Logger logger = LoggerFactory.getLogger(UserClientHandler.class);


    @Override
    public  void handleIO() {
        String userClientId = null;
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            userClientId  = in.readLine() ;
            if(userClientId != null) {
                consumer.accept(userClientId);
                while (true) {
                    if (UserMessagesQueue.getInstance().containsMessageForUser(userClientId)) {
                        Message message = UserMessagesQueue.getInstance().pollMessage(new UserId(Integer.valueOf(userClientId)));
                        if(message != null){
                            String payload = message.getPayload();
                            PrintWriter pw = new PrintWriter(socket.getOutputStream());
                            pw.println(payload);
                            pw.flush();
                        }
                    }
                    else
                        Thread.sleep(1000);
                }
            }

        } catch (Exception e) {//TODO: catch specific exception type
            if(userClientId != null && !userClientId.isEmpty()){
                UserMessagesQueue.getInstance().invalidateUser(userClientId);
            }
            logger.error(e.getMessage(),e);
        }
    }

    @Override
    public void setConsumer(Consumer<String> consumer) {
        this.consumer = consumer;
    }

    @Override
    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
