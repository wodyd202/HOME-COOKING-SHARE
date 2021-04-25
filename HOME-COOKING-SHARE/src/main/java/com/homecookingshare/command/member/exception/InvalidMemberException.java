package com.homecookingshare.command.member.exception;

import com.homecookingshare.common.CustomArgumentException;

import lombok.Getter;

@Getter
public class InvalidMemberException extends CustomArgumentException{
	private static final long serialVersionUID = 1L;
	public InvalidMemberException(String msg, String field) {
		super(msg, field);
	}
}
