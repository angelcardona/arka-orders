package com.arka.store_orders;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class StoreOrdersApplication {

	public static void main(String[] args) {
		SpringApplication.run(StoreOrdersApplication.class, args);
	}

}
