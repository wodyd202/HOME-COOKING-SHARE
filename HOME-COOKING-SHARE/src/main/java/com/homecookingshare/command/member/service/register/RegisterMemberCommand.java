package com.homecookingshare.command.member.service.register;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegisterMemberCommand {
	private String email;
	private String nickName;
	
	@JsonInclude(content = Include.NON_EMPTY)
	private String password;
	
	public void emptyPassword() {
		this.password = null;
	}
	
	public void encodePassword(PasswordEncoder encoder) {
		this.password = encoder.encode(password);
	}
}
