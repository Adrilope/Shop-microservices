package com.springboot.app.products;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
@EntityScan({"com.springboot.app.commons.models.entity"})		// location of the entities
public class SpringbootProductsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootProductsServiceApplication.class, args);
	}

}
