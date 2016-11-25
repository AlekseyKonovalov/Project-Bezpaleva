package com.bezPalevaServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAutoConfiguration
@SpringBootApplication
public class BezPalevaApplication {

	public static void main(String[] args) {
		SpringApplication.run(BezPalevaApplication.class, args);
	}

}
