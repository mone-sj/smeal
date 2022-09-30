package com.campfire.smeal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication //(exclude = { SecurityAutoConfiguration.class })
public class SmealApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmealApplication.class, args);
	}
}
