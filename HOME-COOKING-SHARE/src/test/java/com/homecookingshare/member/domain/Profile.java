package com.homecookingshare.member.domain;

import com.homecookingshare.member.service.register.RegisterMember;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Profile {
	private String nickName;
	private String img;

	public Profile(RegisterMember registerMember) {
		this.nickName = registerMember.getNickName();
	}
}
