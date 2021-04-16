package com.homecookingshare.member.aggregate.exception;

import com.homecookingshare.common.CustomArgumentException;

public class MemberNotFoundException extends CustomArgumentException {
	private static final long serialVersionUID = 1L;

	public MemberNotFoundException(String msg, String field) {
		super(msg, field);
	}
}
