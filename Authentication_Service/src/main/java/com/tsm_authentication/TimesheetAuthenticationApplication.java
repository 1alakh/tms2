package com.tsm_authentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDiscoveryClient
public class TimesheetAuthenticationApplication {

	public static void main(String[] args) {
		SpringApplication.run(TimesheetAuthenticationApplication.class, args);
	}
}
