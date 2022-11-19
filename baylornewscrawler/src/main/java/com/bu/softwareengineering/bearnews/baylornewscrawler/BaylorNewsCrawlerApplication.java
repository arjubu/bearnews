package com.bu.softwareengineering.bearnews.baylornewscrawler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BaylorNewsCrawlerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BaylorNewsCrawlerApplication.class, args);
	}

}
