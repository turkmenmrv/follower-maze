package com.example.followermaze.eventprocessor.controller;

import com.example.followermaze.eventprocessor.user.UserId;
import com.example.followermaze.eventprocessor.user.UserMessagesQueue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserClientControllerTest {
    @Mock
    private  UserMessagesQueue userMessagesQueue;
    private UserClientController userClientController;

    @BeforeEach
    void setUp() {
        userClientController = new UserClientController(userMessagesQueue);
    }

    @Test
    void shouldProcessUser() {
        UserId user = new UserId(1);
        userClientController.processUser("1");
        verify(userMessagesQueue,times(1)).validateUser(user);
    }
}