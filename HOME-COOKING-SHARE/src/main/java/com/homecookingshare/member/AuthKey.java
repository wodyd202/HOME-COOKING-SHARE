package com.homecookingshare.member;

import java.util.UUID;

import com.homecookingshare.member.aggregate.Member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthKey {
	private String email;
	private String authKey;
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof AuthKey) {
			return ((AuthKey) obj).email.equals(this.email) && ((AuthKey) obj).authKey.equals(this.authKey);
		}
		return false;
	}
	
	public static AuthKey createAuthKey(Member member) {
		return new AuthKey(member.getEmail().toString(), UUID.randomUUID().toString().substring(0,6));
	}
}
