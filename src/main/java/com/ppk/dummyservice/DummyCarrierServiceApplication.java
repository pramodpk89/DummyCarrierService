package com.ppk.dummyservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class DummyCarrierServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DummyCarrierServiceApplication.class, args);
	}

}
