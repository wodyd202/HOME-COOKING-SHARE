package com.homecookingshare.command.memberEmailAuth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.homecookingshare.common.ErrorResponse;

@RestControllerAdvice
public class MemberEmailAuthErrorController {
	
	@ExceptionHandler(InvalidEmailAuthenticationException.class)
	public ResponseEntity<ErrorResponse> error(InvalidEmailAuthenticationException e){
		return new ResponseEntity<>(new ErrorResponse(e.getMessage()),HttpStatus.BAD_REQUEST);
	}
}
