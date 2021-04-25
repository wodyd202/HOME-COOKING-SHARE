package com.homecookingshare.command.member.values;

import java.io.Serializable;

import com.homecookingshare.command.member.service.register.RegisterMemberCommand;

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

	public Profile(RegisterMemberCommand registerMember) {
		this.nickName = registerMember.getNickName();
	}

}
