package com.baylor.se.project.bearnews;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class BearnewsApplication {

	public static void main(String[] args) {
		SpringApplication.run(BearnewsApplication.class, args);
	}

}
