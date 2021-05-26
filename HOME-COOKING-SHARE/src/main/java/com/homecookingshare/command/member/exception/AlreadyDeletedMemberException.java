package com.homecookingshare.command.member.exception;

import com.homecookingshare.common.CustomArgumentException;

public class AlreadyDeletedMemberException extends CustomArgumentException{
	private static final long serialVersionUID = 1L;

	public AlreadyDeletedMemberException(String msg) {
		super(msg);
	}

}
