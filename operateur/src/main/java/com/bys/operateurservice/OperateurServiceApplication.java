package com.bys.operateurservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient

public class OperateurServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OperateurServiceApplication.class, args);
	}



}
