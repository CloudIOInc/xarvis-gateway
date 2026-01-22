package com.xoriant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class XarvisGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(XarvisGatewayApplication.class, args);
	}

}
