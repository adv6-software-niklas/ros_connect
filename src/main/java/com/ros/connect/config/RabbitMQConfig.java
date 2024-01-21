package com.ros.connect.config;


import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQConfig {

    // @Value("${spring.rabbitmq.host}")
    private String rabbitmqHost = "rabbitmq-service";

    // @Value("${spring.rabbitmq.port}")
    private int rabbitmqPort = 5672;

    // @Value("${spring.rabbitmq.username}")
    private String rabbitmqUsername = "guest";

    // @Value("${spring.rabbitmq.password}")
    private String rabbitmqPassword = "guest";

    @Bean
    public ConnectionFactory connectionFactory() {
        // Setting up connection with RabbitMQ
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setHost(rabbitmqHost);
        factory.setPort(rabbitmqPort);
        factory.setUsername(rabbitmqUsername);
        factory.setPassword(rabbitmqPassword);
        return factory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }
}