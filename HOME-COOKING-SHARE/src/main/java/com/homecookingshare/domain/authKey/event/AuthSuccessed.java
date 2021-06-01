package com.homecookingshare.domain.authKey.event;

import com.homecookingshare.domain.authKey.TargetEmail;

import lombok.Getter;

@Getter
public class AuthSuccessed extends AbstractMemberAuthKeyEvent{
	
	public AuthSuccessed(TargetEmail email) {
		this.targetEmail = email;
	}
}
