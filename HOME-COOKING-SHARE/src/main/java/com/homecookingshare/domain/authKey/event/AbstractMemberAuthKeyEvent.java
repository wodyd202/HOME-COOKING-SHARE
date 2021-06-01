package com.homecookingshare.domain.authKey.event;

import com.homecookingshare.domain.authKey.TargetEmail;

import lombok.Getter;

@Getter
public class AbstractMemberAuthKeyEvent {
	protected TargetEmail targetEmail;
}
