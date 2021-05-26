package com.homecookingshare.domain.member.event;

import com.homecookingshare.domain.member.Email;

public class DeletedMember extends AbstractMemberEvent{
	public DeletedMember(Email targetUserEmail) {
		this.targetUserEmail = targetUserEmail;
	}
}
