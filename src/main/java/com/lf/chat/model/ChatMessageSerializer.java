package com.lf.chat.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lf.chat.model.ChatMessage;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;


public class ChatMessageSerializer implements Serializer<ChatMessage> {

    @Override
    public void configure( Map map, boolean b ) {

    }

    @Override
    public byte[] serialize( String s, ChatMessage message ) {
        byte[] retVal = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            retVal = objectMapper.writeValueAsString(message).getBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retVal;
    }

    @Override
    public void close() {

    }
}
