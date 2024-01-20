package com.ros.connect.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ros.connect.DTO.RecievedMessage;

@Service
public class Receiver {

    private final Connection conn;
    private int portToCheck;
   
    public Receiver(Connection conn) {
        this.conn = conn;
    }

    public int getPortToCheck() {
        return portToCheck;
    }

    public void setPortToCheck(int value) {
        portToCheck = value;
    }

    @RabbitListener(queues = "config_response")
    public void recieve_config(String message) {
        try {
            // Use Jackson ObjectMapper to deserialize JSON into ConfigMessage
            ObjectMapper objectMapper = new ObjectMapper();
            RecievedMessage recievedMessage = objectMapper.readValue(message, RecievedMessage.class);
    
            System.out.println("Im now testing some shit");
            if (portToCheck == recievedMessage.getPort()){
                System.out.println("Works");
            } else {
                System.out.println("No works");
            }
            // Now you can access the fields like username, password, hostname, and someInteger
            System.out.println("Received config message - Username: " + recievedMessage.getUsername() +
                    ", Password: " + recievedMessage.getPassword() +
                    ", Hostname: " + recievedMessage.getHost() +
                    ", Integer: " + recievedMessage.getPort());
    
            // You can do further processing or set the return message if needed
            // setReturnMessage(configMessage);

            try {
                conn.Connect(recievedMessage.getUsername(), recievedMessage.getPassword(), recievedMessage.getPort(), recievedMessage.getHost(), recievedMessage.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            // Handle the exception appropriately (e.g., log or throw)
            e.printStackTrace();
        }
    }
}