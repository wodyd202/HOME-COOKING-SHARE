package com.homecookingshare.member;

import java.io.Serializable;

import com.homecookingshare.member.service.register.RegisterMember;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Profile implements Serializable{
	private static final long serialVersionUID = 1L;
	private String nickName;
	private String img;

	public Profile(RegisterMember registerMember) {
		this.nickName = registerMember.getNickName();
	}
}
