package com.gkouzias.InternetApps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class InternetAppsApplication {

	public static void main(String[] args) {
		SpringApplication.run(InternetAppsApplication.class, args);
	}

}
