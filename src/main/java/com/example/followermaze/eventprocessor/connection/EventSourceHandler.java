package com.example.followermaze.eventprocessor.connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
  private Logger logger = LoggerFactory.getLogger(EventSourceHandler.class);

  @Override
  public void handleIO() {
    try {
      InputStream is = socket.getInputStream();
      InputStreamReader r = new InputStreamReader(is);
      BufferedReader in = new BufferedReader(r);

      char[] c = new char[3000000];

      int size = in.read(c);

      while (size != -1) {
        logger.info("SIZE :" + size + "***");
        Stream.of(c).forEach(ch -> logger.info(String.valueOf(ch)));
        logger.info("---");
        consumer.accept(String.valueOf(c, 0, size));
        c = new char[30000];
        size = in.read(c);
      }
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
    }
  }

//  @Override
//  public Consumer<String> getConsumer() {
//    return consumer;
//  }

  @Override
  public void setConsumer(final Consumer<String> consumer) {
    this.consumer = consumer;
  }

  @Override
  public void setSocket(final Socket socket) {
    this.socket = socket;
  }
}
