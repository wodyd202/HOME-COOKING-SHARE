package com.homecookingshare.domain.member.event;

import com.homecookingshare.domain.member.Email;

import lombok.Getter;

@Getter
public class ChangedMemberImage extends AbstractMemberEvent{
	private final String imageName;
	public ChangedMemberImage(Email targetUserEmail,String imageName) {
		this.targetUserEmail= targetUserEmail;
		this.imageName = imageName;
	}
}
