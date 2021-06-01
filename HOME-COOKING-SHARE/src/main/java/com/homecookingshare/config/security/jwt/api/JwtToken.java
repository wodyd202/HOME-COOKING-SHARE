package com.homecookingshare.config.security.jwt.api;

import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JwtToken {
	private String accessToken;
	private String refreshToken;

	public boolean equalAccessToken(String token) {
		return this.accessToken.equals(token);
	}

	public boolean equalRefreshToken(String token) {
		return this.refreshToken.equals(token);
	}
	
	public JwtToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public static boolean isValid(String token, String secretKey) {
		try {
			return !expireToken(token, secretKey);
		} catch (Exception e) {
			return false;
		}
	}

	private static boolean expireToken(String token, String secretKey) {
		Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
		boolean remainExpiration = claims.getBody().getExpiration().before(new Date());
		return remainExpiration;
	}

}
