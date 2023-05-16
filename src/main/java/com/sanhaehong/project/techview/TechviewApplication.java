package com.sanhaehong.project.techview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class TechviewApplication {

	public static void main(String[] args) {
		SpringApplication.run(TechviewApplication.class, args);
	}

}
