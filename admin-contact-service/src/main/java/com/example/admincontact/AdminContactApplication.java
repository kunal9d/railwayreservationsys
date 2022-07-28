package com.example.admincontact;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;


@EnableEurekaClient
@SpringBootApplication
@OpenAPIDefinition
public class AdminContactApplication {


	public static void main(String[] args) {
		SpringApplication.run(AdminContactApplication.class, args);
	}


}
