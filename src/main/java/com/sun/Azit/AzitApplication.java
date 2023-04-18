package com.sun.Azit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class AzitApplication {

	public static void main(String[] args) {
		SpringApplication.run(AzitApplication.class, args);

	}

	@GetMapping(value = "/")
	public String HellowWorld(){
		return "Hello world";
	}



}
