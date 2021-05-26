package com.homecookingshare.member;

import com.homecookingshare.command.member.exception.InvalidMemberException;
import com.homecookingshare.command.member.service.MemberCommand.ChangePasswordCommand;
import com.homecookingshare.common.Validator;

public class ChangePasswordCommandValidator implements Validator<ChangePasswordCommand> {

	@Override
	public void validate(ChangePasswordCommand obj) {
		String changePassword = obj.getChangePassword();
		String originPassword = obj.getOriginPassword();
		
		verifyNotEmptyStringValue(originPassword, new InvalidMemberException("기존 비밀번호를 입력해주세요."));
		verifyNotEmptyStringValue(changePassword, new InvalidMemberException("변경할 비밀번호를 입력해주세요."));
	}

}
