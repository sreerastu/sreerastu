package com.app.sreerastu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {"com.app.sreerastu.domain"})
public class SreerastuApplication {

	public static void main(String[] args) {
		SpringApplication.run(SreerastuApplication.class, args);
	}

}
