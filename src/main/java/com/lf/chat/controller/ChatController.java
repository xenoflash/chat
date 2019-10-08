package com.lf.chat.controller;

import com.lf.chat.kafka.consumer.Receiver;
import com.lf.chat.kafka.producer.Sender;
import com.lf.chat.model.ChattingMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import com.lf.chat.model.ChatMessage;

@Controller
public class ChatController {

    @Autowired
    private Sender sender;

    @Autowired
    private Receiver receiver;
    private static String BOOT_TOPIC = "chatting";

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public void sendMessage(ChattingMessage message) throws Exception {
        //Thread.sleep(1000); // simulated delay
        sender.send(BOOT_TOPIC, message.getMessage() + "|" + message.getUser());
    }
    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
	// Add username in web socket session
	headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
	return chatMessage;
    }

}