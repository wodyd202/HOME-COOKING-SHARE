package com.homecookingshare.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomArgumentException extends IllegalArgumentException {
	private static final long serialVersionUID = 1L;
	protected String msg;
	protected String field;
}
