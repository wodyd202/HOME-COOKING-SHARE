package com.homecookingshare.command.member.exception;

import com.homecookingshare.common.CustomArgumentException;

public class MemberNotFoundException extends CustomArgumentException {
	private static final long serialVersionUID = 1L;

	public MemberNotFoundException(String msg) {
		super(msg);
	}
}
