package com.homecookingshare.domain.member.event;

import com.homecookingshare.domain.member.Email;

import lombok.Getter;

@Getter
public class AbstractMemberEvent {
	protected Email targetUserEmail;
}
