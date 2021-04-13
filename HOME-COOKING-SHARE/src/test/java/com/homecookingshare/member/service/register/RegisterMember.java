package com.homecookingshare.member.service.register;

import com.homecookingshare.member.domain.aggregate.Member;

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
	
	public Member toEntity() {
		return new Member(this);
	}
}
