package com.homecookingshare.command.recipe.exception;

import com.homecookingshare.common.CustomArgumentException;

public class RecipeNotFoundException extends CustomArgumentException{
	private static final long serialVersionUID = 1L;
	public RecipeNotFoundException(String msg) {
		super(msg);
	}
}
