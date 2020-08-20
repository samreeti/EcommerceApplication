package com.cts.ecommerce.dealer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableEurekaClient
@EnableSwagger2
public class DealerApplication {

	private static final Logger log= LoggerFactory.getLogger(DealerApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(DealerApplication.class, args);
		log.info("Application started");
	}

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate()
	{
		return new RestTemplate();
	}


}
