package com.homecookingshare.config.security.jwt.api;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccessTokenDto {
	private String email;
	private String password;
	
	public AccessTokenDto(String email) {
		this.email = email;
	}
}
