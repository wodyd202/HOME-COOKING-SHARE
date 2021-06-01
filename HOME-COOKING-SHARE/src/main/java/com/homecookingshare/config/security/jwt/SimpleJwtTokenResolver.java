package com.homecookingshare.config.security.jwt;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.homecookingshare.config.security.jwt.api.JwtToken;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SimpleJwtTokenResolver implements JwtTokenResolver{
	private final String X_AUTH_TOKEN_HEADER = "Authorization";
	public JwtToken resolve(HttpServletRequest request) throws InvalidJwtTokenException {
		String reqJwtToken = request.getHeader(X_AUTH_TOKEN_HEADER);
		if(reqJwtToken == null || reqJwtToken.isEmpty()) {
			throw new InvalidJwtTokenException();
		}
		return new JwtToken(reqJwtToken);
	}
	
	public class InvalidJwtTokenException extends Exception{
		private static final long serialVersionUID = 1L;
	}
}
	