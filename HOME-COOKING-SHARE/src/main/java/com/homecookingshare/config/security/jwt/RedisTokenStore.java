package com.homecookingshare.config.security.jwt;

import java.io.IOException;
import java.time.Duration;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.homecookingshare.config.security.jwt.api.JwtToken;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RedisTokenStore implements TokenStore{
	private final RedisTemplate<String, Object> template;
	private final ObjectMapper objectMapper;
	
	@Value("${redis.token-key}")
	private String TOKEN_KEY;
	
	public void save(String userPk, JwtToken token) {
		try {
			template.opsForValue().set(TOKEN_KEY + userPk, objectMapper.writeValueAsString(token), Duration.ofHours(1L));
		} catch (JsonProcessingException e) {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public Optional<JwtToken> findByUser(String userPk) {
		Object result = template.opsForValue().get(TOKEN_KEY + userPk);
		if(result == null) {
			return Optional.ofNullable(null);
		}
		try {
			JwtToken readValue = objectMapper.readValue(result.toString(), JwtToken.class);
			return Optional.of(readValue);
		} catch (IOException e) {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public boolean existByUser(String userPk) {
		return this.findByUser(userPk).isPresent();
	}
	
}
