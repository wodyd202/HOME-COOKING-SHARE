package com.homecookingshare.command.memberEmailAuth.exception;

import com.homecookingshare.common.CustomArgumentException;

import lombok.Getter;

@Getter
public class InvalidEmailAuthenticationException extends CustomArgumentException{
	private static final long serialVersionUID = 1L;

	public InvalidEmailAuthenticationException(String msg) {
		super(msg);
	}
}
