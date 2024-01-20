package com.ros.connect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.ros.connect.config.rabbitQueue.RosConfigQueue;

@SpringBootApplication
@EnableConfigurationProperties(RosConfigQueue.class)
public class ConnectApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConnectApplication.class, args);
	}

}
