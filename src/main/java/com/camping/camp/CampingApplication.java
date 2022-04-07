package com.camping.camp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = {"com.camping.camp"})
public class CampingApplication {

	public static void main(String[] args) {
		SpringApplication.run(CampingApplication.class, args);
	}

}
