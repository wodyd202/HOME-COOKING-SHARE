package com.homecookingshare.command.member.infra.validator;

import org.springframework.stereotype.Component;

import com.homecookingshare.command.member.model.MemberCommand.RegisterMember;
import com.homecookingshare.common.Validator;

@Component
public class RegisterMemberValidator implements Validator<RegisterMember>, MemberValidator {

	@Override
	public void validate(RegisterMember obj) {
		String email = obj.getEmail();
		String nickName = obj.getNickName();
		String password = obj.getPassword();
		
		verifyNotEmptyStringValue(email, invalidMember("사용자 이메일을 입력해주세요."));
		verifyNotEmptyStringValue(nickName, invalidMember("사용자 닉네임을 입력해주세요."));
		verifyNotEmptyStringValue(password, invalidMember("사용자 비밀번호를 입력해주세요."));
		verifyContainUnAllowedSpecialChar(email, invalidMember("사용자 이메일에 허용하지 않는 특수문자가 포함되어있습니다."));
		verifyContainUnAllowedSpecialChar(nickName, invalidMember("사용자 닉네임에 허용하지 않는 특수문자가 포함되어있습니다."));
		verifyContainUnAllowedSpecialChar(password, invalidMember("사용자 비밀번호에 허용하지 않는 특수문자가 포함되어있습니다."));
		verifyRegexPatternStringValue(email, "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$",1,100,invalidMember("이메일 형식으로 입력해주세요."));
	}

}
