package com.homecookingshare.domain.member.event;

import com.homecookingshare.domain.member.Email;
import com.homecookingshare.domain.member.Password;
import com.homecookingshare.domain.member.Profile;

import lombok.Getter;

@Getter
public class RegisterdMember extends AbstractMemberEvent{
	private final Password password;
	private final Profile profile;
	
	public RegisterdMember(Email email,Password password, Profile profile) {
		this.targetUserEmail = email;
		this.password = password;
		this.profile = profile;
	}
}
