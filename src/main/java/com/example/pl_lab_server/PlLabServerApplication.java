package com.example.pl_lab_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class PlLabServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlLabServerApplication.class, args);
	}

}
