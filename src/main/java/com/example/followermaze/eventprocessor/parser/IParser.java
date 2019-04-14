package com.example.followermaze.eventprocessor.parser;

import com.example.followermaze.eventprocessor.message.Message;

import java.util.List;

public interface IParser {
    public List<Message> createMessage(String message);
}
