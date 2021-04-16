package com.homecookingshare.member.aggregate.exception;

import com.homecookingshare.common.CustomArgumentException;

public class AlreadyExistMemberException extends CustomArgumentException{
	private static final long serialVersionUID = 1L;
	public AlreadyExistMemberException(String msg, String field) {
		super(msg, field);
	}
}
