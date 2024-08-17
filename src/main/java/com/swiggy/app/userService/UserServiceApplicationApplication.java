package com.swiggy.app.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author : Samiullah Makandar
 * @purpose :
 */

@SpringBootApplication(scanBasePackages = {"com.swiggy.app.demo"})
public class UserServiceApplicationApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplicationApplication.class, args);
	}

}
