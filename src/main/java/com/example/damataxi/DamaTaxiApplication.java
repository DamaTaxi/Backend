package com.example.damataxi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DamaTaxiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DamaTaxiApplication.class, args);
	}

}
