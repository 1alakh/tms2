package com.tsm_user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TimesheetUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(TimesheetUserApplication.class, args);
	}
}
