package com.lf.chat.model;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class ChatMessageDeserializer implements Deserializer<ChatMessage> {

    @Override
    public void configure( Map<String, ?> map, boolean b ) {

    }

    @Override
    public ChatMessage deserialize( String s, byte[] bytes ) {
        ObjectMapper mapper = new ObjectMapper();
        ChatMessage message = null;
        try {
            message = mapper.readValue(bytes, ChatMessage.class);
        } catch (Exception e) {

            e.printStackTrace();
        }
        return message;
    }

    @Override
    public void close() {

    }
}
