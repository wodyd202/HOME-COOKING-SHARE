package com.homecookingshare.domain.member.event;

import com.homecookingshare.domain.member.Email;
import com.homecookingshare.domain.member.Password;

import lombok.Getter;

@Getter
public class ChangedMemberPassword extends AbstractMemberEvent {
	private final Password changePassword;
	public ChangedMemberPassword(Email targetUserEmail, Password changePassword) {
		this.targetUserEmail = targetUserEmail;
		this.changePassword = changePassword;
	}
}
