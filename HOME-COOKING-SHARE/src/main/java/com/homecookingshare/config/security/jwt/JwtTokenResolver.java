package com.homecookingshare.config.security.jwt;

import javax.servlet.http.HttpServletRequest;

import com.homecookingshare.config.security.jwt.SimpleJwtTokenResolver.InvalidJwtTokenException;
import com.homecookingshare.config.security.jwt.api.JwtToken;

public interface JwtTokenResolver {
	JwtToken resolve(HttpServletRequest request) throws InvalidJwtTokenException;
}
