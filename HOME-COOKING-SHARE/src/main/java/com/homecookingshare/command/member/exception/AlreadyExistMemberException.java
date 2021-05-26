package com.homecookingshare.command.member.exception;

import com.homecookingshare.common.CustomArgumentException;

public class AlreadyExistMemberException extends CustomArgumentException{
	private static final long serialVersionUID = 1L;
	public AlreadyExistMemberException(String msg) {
		super(msg);
	}
}
