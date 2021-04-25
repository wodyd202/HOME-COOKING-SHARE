package com.homecookingshare.command.member.entity;

import java.util.UUID;

import com.homecookingshare.command.member.values.Email;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthKey {
	private String email;
	private String authKey;

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AuthKey) {
			return ((AuthKey) obj).authKey.equals(this.authKey) && ((AuthKey) obj).email.equals(this.email);
		}
		return false;
	}

	public static AuthKey createAuthKey(Email member) {
		return new AuthKey(member.toString(), UUID.randomUUID().toString().substring(0, 6));
	}
}
