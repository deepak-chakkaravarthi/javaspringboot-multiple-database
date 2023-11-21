package com.flywaydb.FlyWayDemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;

@SpringBootApplication(scanBasePackages = "com.flywaydb.FlyWayDemo")
//@SpringBootApplication
public class FlyWayDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlyWayDemoApplication.class, args);
	}


}
