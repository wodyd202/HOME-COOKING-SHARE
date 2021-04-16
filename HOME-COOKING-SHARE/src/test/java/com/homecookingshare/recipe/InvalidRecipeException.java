package com.homecookingshare.recipe;

import com.homecookingshare.common.CustomArgumentException;

public class InvalidRecipeException extends CustomArgumentException{
	private static final long serialVersionUID = 1L;

	public InvalidRecipeException(String msg, String field) {
		super(msg, field);
	}
}
