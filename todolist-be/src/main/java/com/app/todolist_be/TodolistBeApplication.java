package com.app.todolist_be;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@OpenAPIDefinition(info =@Info(title = "Todolist App", version = "1.0", description = "Todolist App API"))
@EnableAsync
public class TodolistBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodolistBeApplication.class, args);
	}

}
