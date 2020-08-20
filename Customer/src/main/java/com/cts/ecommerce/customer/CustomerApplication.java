package com.cts.ecommerce.customer;

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
public class CustomerApplication {

	private static final Logger log= LoggerFactory.getLogger(CustomerApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(CustomerApplication.class, args);
		log.info("Customer service started");
	}


	@Bean
	@LoadBalanced
	public RestTemplate restTemplate()
	{
		return new RestTemplate();
	}

}
