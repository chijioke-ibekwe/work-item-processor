package com.project.workitemprocessor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.project.workitemprocessor.repository.impl")
@SpringBootApplication
public class WorkItemProcessorApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorkItemProcessorApplication.class, args);
	}

}
