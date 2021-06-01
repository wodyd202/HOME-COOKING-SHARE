package com.homecookingshare.command.memberEmailAuth.service;

import com.homecookingshare.command.memberEmailAuth.model.MemberEmailAuthCommand;
import com.homecookingshare.common.Validator;
import com.homecookingshare.domain.authKey.event.RegisterdEmailAuthKey;
import com.homecookingshare.domain.member.event.RegisterdMember;

public interface MemberEmailAuthService {
	void verifycation(
			Validator<MemberEmailAuthCommand.Verifycation> validator,
			MemberEmailAuthCommand.Verifycation command
		);

	void create(
			RegisterdMember event
		);

	void create(
			RegisterdEmailAuthKey event
		);
}
