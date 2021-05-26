package com.homecookingshare.command.member.infra.validator;

import com.homecookingshare.command.member.service.MemberCommand.RegisterMemberCommand;
import com.homecookingshare.common.Validator;

public class RegisterMemberCommandValidator implements Validator<RegisterMemberCommand>, MemberValidator {

	@Override
	public void validate(RegisterMemberCommand obj) {
		String email = obj.getEmail();
		String nickName = obj.getNickName();
		String password = obj.getPassword();
		
		verifyNotEmptyStringValue(email, invalidMember("사용자 이메일을 입력해주세요."));
		verifyNotEmptyStringValue(nickName, invalidMember("사용자 닉네임을 입력해주세요."));
		verifyNotEmptyStringValue(password, invalidMember("사용자 비밀번호를 입력해주세요."));
		verifyRegexPatternStringValue(email, "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$",1,100,invalidMember("이메일 형식으로 입력해주세요."));
		verifyRegexPatternStringValue(nickName, "^[가-힣a-zA-Z]*$", 1, 8, invalidMember("사용자 닉네임은 한글 혹은 영어 조합 1자 이상 8자 이하로 입력해주세요."));
		verifyRegexPatternStringValue(password, "^[a-zA-Z0-9]*$", 8, 15, invalidMember("사용자 비밀번호는 영어(소,대문자)와 숫자 조합으로 8자 이상 15자 이하로 입력해주세요."));
	}

}
