package com.example.followermaze.eventprocessor.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class EventSourceHandler implements ISocketHandler {
    private Socket socket;
    private Consumer<String> consumer;

    @Override
    public void handleIO() {
        try {
            InputStream is = socket.getInputStream();
            InputStreamReader r = new InputStreamReader(is);
            BufferedReader in = new BufferedReader(r);

            char[] c = new char[3000000];

            int size = in.read(c);

            while(size != -1){
                    System.out.println("SIZE :" + size + "***");
                    Stream.of(c).forEach(ch -> System.out.print(ch));
                    System.out.println("---");

                consumer.accept(String.valueOf(c, 0, size));

                c = new char[30000];

                size = in.read(c);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Consumer<String> getConsumer() {
        return consumer;
    }

    @Override
    public void setConsumer(final Consumer<String> consumer) {
        this.consumer = consumer;
    }

    @Override
    public void setSocket(final Socket socket) {
        this.socket = socket;
    }
}
