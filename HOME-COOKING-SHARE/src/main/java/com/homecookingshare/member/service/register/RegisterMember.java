package com.homecookingshare.member.service.register;

import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterMember {
	private String email;
	private String nickName;
	private String password;
	
	public void encodePassword(PasswordEncoder encoder) {
		this.password = encoder.encode(this.password);
	}
}
