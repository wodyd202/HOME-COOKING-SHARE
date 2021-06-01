package com.homecookingshare.config.security.jwt.exception;

public class InvalidAccessTokenException extends IllegalArgumentException {
	private static final long serialVersionUID = 1L;

	public InvalidAccessTokenException(String msg) {
		super(msg);
	}
}
