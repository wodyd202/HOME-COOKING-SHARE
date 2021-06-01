package com.homecookingshare.config.security.jwt;

import java.util.Optional;

import com.homecookingshare.config.security.jwt.api.JwtToken;

public interface TokenStore {
	void save(String userPk, JwtToken token);

	Optional<JwtToken> findByUser(String userPk);
	
	boolean existByUser(String userPk);
}
