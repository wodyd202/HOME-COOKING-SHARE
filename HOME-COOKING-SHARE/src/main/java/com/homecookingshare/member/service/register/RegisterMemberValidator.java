package com.homecookingshare.member.service.register;

import com.homecookingshare.common.Validator;
import com.homecookingshare.member.aggregate.exception.InvalidMemberException;

public class RegisterMemberValidator implements Validator<RegisterMember> {

	@Override
	public void validate(RegisterMember obj) {
		String email = obj.getEmail();
		String nickName = obj.getNickName();
		String password = obj.getPassword();
		verfyNotEmptyStringValue(email, invalidMember("사용자 이메일을 입력해주세요.", "email"));
		verfyNotEmptyStringValue(nickName, invalidMember("사용자 닉네임을 입력해주세요.", "nickname"));
		verfyNotEmptyStringValue(password, invalidMember("사용자 비밀번호를 입력해주세요.", "password"));
		verfyRegexPatternStringValue(email, "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$",invalidMember("이메일 형식으로 입력해주세요.", "email"));
		verfyRegexPatternStringValue(nickName, "^[가-힣a-zA-Z]*$", 1, 8, invalidMember("사용자 닉네임은 한글 혹은 영어 조합 1자 이상 8자 이하로 입력해주세요.", "nickname"));
		verfyRegexPatternStringValue(password, "^[a-zA-Z0-9]*$", 8, 15, invalidMember("사용자 비밀번호는 영어(소,대문자)와 숫자 조합으로 8자 이상 15자 이하로 입력해주세요.", "password"));
	}

	private InvalidMemberException invalidMember(String msg, String field) {
		return new InvalidMemberException(msg, field);
	}

}
