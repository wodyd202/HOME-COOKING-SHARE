package com.homecookingshare.command.member.infra.validator;

import com.homecookingshare.command.member.exception.InvalidMemberException;

public interface MemberValidator {
	default public InvalidMemberException invalidMember(String msg) {
		return new InvalidMemberException(msg);
	}
}
