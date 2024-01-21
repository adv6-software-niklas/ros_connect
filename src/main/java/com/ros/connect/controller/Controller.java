package com.ros.connect.controller;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.web.bind.annotation.*;

import com.ros.connect.service.Connection;
import com.ros.connect.service.Receiver;
import com.ros.connect.service.testsend;

@RestController
@RequestMapping("connect")
public class Controller {

    private final Connection _conn;
    private final Receiver reciever;
    private final testsend testSend;

    public Controller(Connection conn, Receiver reciever, testsend testSend){
        _conn = conn;
        this.reciever = reciever;
        this.testSend = testSend;
    }

    @GetMapping("/test")
    public String get(){

        reciever.setPortToCheck(22);
        String errorMessage = "Geen error";
        try {
            if (!_conn.Connect()) {
                errorMessage = "Geen verbinding";
            }
        }
        catch (Exception e) {
            System.out.println(e);
            errorMessage = "Geen goede code bro";
        }
        return errorMessage;
    }

    @GetMapping("/connect/{port}/{passwordHashed}")
    public String get2(@PathVariable int port, @PathVariable String passwordHashed){

        reciever.setPortToCheck(port);
        testSend.sendConfigRequest(passwordHashed);
        String errorMessage = "Geen error";
        // testSend.sendMessage(); //pass conf-id
        return errorMessage;
    }

    @GetMapping("/testAsync")
    public CompletableFuture<String> getAsync() {
        CompletableFuture<AtomicBoolean> connectFuture = _conn.connectAsync();
    
        CompletableFuture<String> responseFuture = connectFuture.thenApplyAsync(result -> {
            if (result.get()) {
                return "Done";
            } else {
                return "Connection failed!";
            }
        }).exceptionally(ex -> "Error occurred: " + ex.getMessage());
        return responseFuture;
    }

    // @GetMapping("/help")
    // public String sendRabbit() {
    //     return "gangster";
    // }
}
