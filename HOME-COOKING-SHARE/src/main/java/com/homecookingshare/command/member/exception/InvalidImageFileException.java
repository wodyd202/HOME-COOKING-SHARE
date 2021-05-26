package com.homecookingshare.command.member.exception;

import com.homecookingshare.common.CustomArgumentException;

public class InvalidImageFileException extends CustomArgumentException {
	private static final long serialVersionUID = 1L;
	public InvalidImageFileException(String msg) {
		super(msg);
	}

}
