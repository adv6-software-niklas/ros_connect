package com.ros.connect.config.rabbitQueue;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@ConfigurationProperties("ros")
public record RosConfigQueue(String username, String password, String host, int port) {
    
    static final String topicExchangeName = "amq.topic";
    static final String queueName = "config_request";
   
   //  @Bean
   //  @Primary
   //  Queue config_request() {
   //     return new Queue(queueName, false);
   //  }
    
   //  @Bean
   //  Queue process_response() {
   //    return new Queue("proccess_response", false);
   //  }

   //  @Bean
   //  Queue config_response() {
   //    return new Queue("config_response", false);
   //  }

   //  @Bean
   //  TopicExchange exchange() {
   //     return new TopicExchange(topicExchangeName);
   //  }

   //  @Bean
   //  Binding binding(Queue queue, TopicExchange exchange) {
   //     return BindingBuilder.bind(queue).to(exchange).with("foo.bar.baz");
   //  }
}