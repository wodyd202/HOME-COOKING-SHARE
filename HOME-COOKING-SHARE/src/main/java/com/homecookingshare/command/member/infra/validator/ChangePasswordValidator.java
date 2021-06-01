package com.homecookingshare.command.member.infra.validator;

import org.springframework.stereotype.Component;

import com.homecookingshare.command.member.exception.InvalidMemberException;
import com.homecookingshare.command.member.model.MemberCommand.ChangePassword;
import com.homecookingshare.common.Validator;

@Component
public class ChangePasswordValidator implements Validator<ChangePassword> {

	@Override
	public void validate(ChangePassword obj) {
		String changePassword = obj.getChangePassword();
		String originPassword = obj.getOriginPassword();
		
		verifyNotEmptyStringValue(originPassword, new InvalidMemberException("기존 비밀번호를 입력해주세요."));
		verifyNotEmptyStringValue(changePassword, new InvalidMemberException("변경할 비밀번호를 입력해주세요."));
	}

}
