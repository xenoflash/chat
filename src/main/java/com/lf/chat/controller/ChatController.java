package com.lf.chat.controller;

import com.lf.chat.kafka.consumer.Receiver;
import com.lf.chat.kafka.producer.Sender;
import com.lf.chat.model.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @Autowired
    private Sender sender;

    @Autowired
    private Receiver receiver;
    private static String BOOT_TOPIC = "public";

    /*@MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
	return chatMessage;
    }*/

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(ChatMessage chatMessage) {
        sender.send(BOOT_TOPIC,chatMessage);
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
	// Add username in web socket session
	headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
	return chatMessage;
    }

}