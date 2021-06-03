package com.homecookingshare.command.recipe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.homecookingshare.common.ErrorResponse;

@RestControllerAdvice
public class RecipeErrorController {

	@ExceptionHandler(InvalidRecipeException.class)
	public ResponseEntity<ErrorResponse> error(InvalidRecipeException e) {
		return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(RecipeNotFoundException.class)
	public ResponseEntity<ErrorResponse> error(RecipeNotFoundException e) {
		return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
	}
}
