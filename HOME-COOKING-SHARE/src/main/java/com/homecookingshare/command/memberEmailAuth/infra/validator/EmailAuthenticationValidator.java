package com.homecookingshare.command.memberEmailAuth.infra.validator;

import org.springframework.stereotype.Component;

import com.homecookingshare.command.memberEmailAuth.exception.InvalidEmailAuthenticationException;
import com.homecookingshare.command.memberEmailAuth.model.MemberEmailAuthCommand.Verifycation;
import com.homecookingshare.common.Validator;

@Component
public class EmailAuthenticationValidator implements Validator<Verifycation> {

	@Override
	public void validate(Verifycation obj) {
		String requireVerificationTargetEmail = obj.getRequireVerificationTargetEmail();
		String key = obj.getKey();
		
		verifyNotEmptyStringValue(requireVerificationTargetEmail, new InvalidEmailAuthenticationException("인증할 사용자 이메일을 입력해주세요."));
		verifyNotEmptyStringValue(key, new InvalidEmailAuthenticationException("인증 키를 입력해주세요."));
	}

}
