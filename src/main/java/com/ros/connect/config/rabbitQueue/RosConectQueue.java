package com.ros.connect.config.rabbitQueue;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

@ConfigurationProperties("connect")
public record RosConectQueue(String username, String password, String host, int port) {
    
    static final String topicExchangeName = "amq.topic";
    static final String queueName = "connect_response"; //TODO: Think about which queue this service creates and sends to
   
    // @Bean
    // Queue queue() {
    //    return new Queue(queueName, false);
    // }

    // @Bean
    // TopicExchange exchange() {
    //    return new TopicExchange(topicExchangeName);
    // }

    // @Bean
    // Binding binding(Queue queue, TopicExchange exchange) {
    //    return BindingBuilder.bind(queue).to(exchange).with("foo.bar.connect");
    // }
}
