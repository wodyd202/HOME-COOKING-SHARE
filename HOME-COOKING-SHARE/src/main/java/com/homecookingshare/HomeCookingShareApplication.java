package com.homecookingshare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.customConfig.EnableRedisInitModule;

@SpringBootApplication
@EnableRedisInitModule
public class HomeCookingShareApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomeCookingShareApplication.class, args);
	}

}
