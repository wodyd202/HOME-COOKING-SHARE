package com.homecookingshare.config.security.jwt;

import java.util.Date;
import java.util.List;

import com.homecookingshare.config.security.jwt.api.JwtToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtTokenProvider {
	private final TokenStore store;
	private final String secretKey;
	private final long accessTokenExpireAt;
	private final long refreshTokenExpireAt;

	public JwtToken provideToken(String userPk, List<String> roles) {
		Claims claims = Jwts.claims().setSubject(userPk);
		claims.put("role", roles);
		
		Date now = new Date();
		String accessToken = createToken(claims, now, new Date(now.getTime() + accessTokenExpireAt));
		String refreshToken = createToken(claims, now, new Date(now.getTime() + refreshTokenExpireAt));
		JwtToken token = new JwtToken(accessToken,refreshToken);
		store.save(userPk, token);
		return token;
	}

	private String createToken(Claims claims, Date now, Date expireDate) {
		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(now)
			.setExpiration(expireDate)
			.signWith(SignatureAlgorithm.HS256, secretKey)
			.compact();
	}
}
