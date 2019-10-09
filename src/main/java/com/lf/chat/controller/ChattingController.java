package com.lf.chat.controller;

import com.lf.chat.model.ChattingMessage;
import com.lf.chat.kafka.consumer.Receiver;
import com.lf.chat.kafka.producer.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChattingController {

    @Autowired
    private Sender sender;

    @Autowired
    private Receiver receiver;
    private static String BOOT_TOPIC = "chatting";

    @MessageMapping("/message")
    public void sendMessage(ChattingMessage message) throws Exception {
        //Thread.sleep(2000); // simulated delay
        sender.send(BOOT_TOPIC, message.getMessage() + "|" + message.getUser());
    }

    @MessageMapping("/file")
    @SendTo("/topic/chatting")
    public ChattingMessage sendFile( ChattingMessage message) throws Exception {
        return new ChattingMessage(message.getFileName(), message.getRawData(), message.getUser());
    }
}