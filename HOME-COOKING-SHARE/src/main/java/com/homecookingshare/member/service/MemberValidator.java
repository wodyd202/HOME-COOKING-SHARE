package com.homecookingshare.member.service;

import com.homecookingshare.command.member.exception.InvalidMemberException;

public interface MemberValidator {
	default public InvalidMemberException invalidMember(String msg, String field) {
		return new InvalidMemberException(msg, field);
	}
}
