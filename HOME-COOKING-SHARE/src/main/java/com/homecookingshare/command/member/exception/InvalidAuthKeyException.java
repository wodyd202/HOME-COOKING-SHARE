package com.homecookingshare.command.member.exception;

import com.homecookingshare.common.CustomArgumentException;

import lombok.Getter;

@Getter
public class InvalidAuthKeyException extends CustomArgumentException{
	private static final long serialVersionUID = 1L;

	public InvalidAuthKeyException(String msg) {
		super(msg);
	}
}
