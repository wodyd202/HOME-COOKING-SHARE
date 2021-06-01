package com.homecookingshare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import com.customConfig.EnableRedisInitModule;

@SpringBootApplication
//@EnableRedisInitModule
@EnableAsync(proxyTargetClass = true)
public class HomeCookingShareApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomeCookingShareApplication.class, args);
	}

}
