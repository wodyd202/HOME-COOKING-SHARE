package com.homecookingshare.domain.member.event;

import com.homecookingshare.domain.member.Member;

import lombok.Getter;

@Getter
public class RegisterdMember extends AbstractMemberEvent{
	private final Member member;
	
	public RegisterdMember(Member member) {
		this.targetUserEmail = member.getEmail();
		this.member = member;
	}
}
