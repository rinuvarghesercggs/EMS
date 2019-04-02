package com.EMS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;


import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class TimetrackerApplication  	//extends SpringBootServletInitializer

{

	
	public static void main(String[] args) {
		SpringApplication.run(TimetrackerApplication.class, args);
	}

	
	
	@Bean
	public ObjectMapper configureObjectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		// set properties for objectmapper here

		return mapper;
	}
}
