package com.example.followermaze.eventprocessor.controller;

import com.example.followermaze.eventprocessor.connection.ISocketServer;
import com.example.followermaze.eventprocessor.message.Message;
import com.example.followermaze.eventprocessor.message.MessageHeap;
import com.example.followermaze.eventprocessor.parser.Parser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventSourceControllerTest {

    @Mock
    private Parser parser;
    @Mock
    private ISocketServer socketServer;

    @Mock
    private MessageHeap messageHeap;

    private EventSourceController eventSourceController;

    @BeforeEach
    void setUp() {
        eventSourceController = new EventSourceController(parser,socketServer,messageHeap);
    }

    @Test
    void shouldProcessValidMessages() {
        String input = "TEST";
        eventSourceController.processMessage(input);
        verify(parser).createMessage(input);
    }

    @Test
    void shouldAddMessagesToHeap() {
        Message message = new Message(1L,null,null,null,"");
        when(parser.createMessage(anyString())).thenReturn(Arrays.asList(message));
        eventSourceController.processMessage("TEST");
        verify(messageHeap,times(1)).addMessage(message);
    }
}