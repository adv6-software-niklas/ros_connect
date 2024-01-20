package com.ros.connect.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class testsend {

    private final RabbitTemplate rabbitTemplate;

    public testsend(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendProcessingMessage(String text){
        System.out.println("Sending message...");
        rabbitTemplate.convertAndSend("amq.topic", "processing.supply", text);
    }

    public void sendConfigRequest(String text) {
        System.out.println("Sending message...");
        rabbitTemplate.convertAndSend("amq.topic", "ros.config.request", text);        
    }

    public void sendMessage(int text){
        System.out.println("Sending message...");
        rabbitTemplate.convertAndSend("amq.topic", "ros.config.request", text);
    }
}