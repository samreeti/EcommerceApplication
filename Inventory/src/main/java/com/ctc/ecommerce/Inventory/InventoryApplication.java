package com.ctc.ecommerce.Inventory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
public class InventoryApplication {

	private static final Logger log= LoggerFactory.getLogger(InventoryApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(InventoryApplication.class, args);
		log.info("Application started");
	}

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate()
	{
		return new RestTemplate();
	}

}
