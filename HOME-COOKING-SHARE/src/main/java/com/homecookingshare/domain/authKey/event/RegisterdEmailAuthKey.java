package com.homecookingshare.domain.authKey.event;

import com.homecookingshare.domain.authKey.TargetEmail;

import lombok.Getter;

@Getter
public class RegisterdEmailAuthKey extends AbstractMemberAuthKeyEvent{
	
	public RegisterdEmailAuthKey(TargetEmail email) {
		this.targetEmail = email;
	}
}
