package com.customConfig;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class RedisInitialConfig {

	private final RedisTemplate<String, Object> template;

	@PostConstruct
	public void init() throws Exception {
		template.getConnectionFactory().getConnection().flushAll();
	}
}
