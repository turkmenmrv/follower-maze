package com.example.followermaze.eventprocessor.connection;

import com.example.followermaze.eventprocessor.Utils;
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
  private final Logger logger = LoggerFactory.getLogger(EventSourceHandler.class);

  @Override
  public void handleIO() {
    try {
      InputStream is = socket.getInputStream();
      InputStreamReader r = new InputStreamReader(is);
      BufferedReader in = new BufferedReader(r);

      char[] c = new char[Utils.getReadBufferSize()];

      //in.mark(31);
      int size = in.read(c);


      String chunk = String.valueOf(c, 0, size);
      String begin = "";
      String remainingPrev = "";

      while (size != -1) {
        logger.info("SIZE :" + size + "***");
        Stream.of(c).forEach(ch -> logger.info(String.valueOf(ch)));
        logger.info("---");



        String[] parts = splitToParts(chunk);
        begin = parts[0];
        consumer.accept(remainingPrev + begin);

        remainingPrev = parts[1];
        c = new char[Utils.getReadBufferSize()];
        size = in.read(c);
        if(size > -1){
          chunk = String.valueOf(c, 0, size);
        }
      }
      consumer.accept(remainingPrev);
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
    }
  }

  private String[] splitToParts(String chunk){
    int idxLastEnd = chunk.lastIndexOf("\n");
    String begin = chunk.substring(0, idxLastEnd + 1);
    String end = "";
    if(idxLastEnd < chunk.length()-1){
      end = chunk.substring(idxLastEnd+1);
    }
    String[] ret = {begin, end};
    return ret;
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
