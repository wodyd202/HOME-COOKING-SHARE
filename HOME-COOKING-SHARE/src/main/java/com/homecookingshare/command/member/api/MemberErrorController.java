package com.homecookingshare.command.member.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.homecookingshare.command.member.exception.AlreadyExistMemberException;
import com.homecookingshare.command.member.exception.InvalidAuthKeyException;
import com.homecookingshare.command.member.exception.InvalidMemberException;
import com.homecookingshare.command.member.exception.MemberNotFoundException;
import com.homecookingshare.common.ErrorResponse;

@RestControllerAdvice
public class MemberErrorController {

	@ExceptionHandler(AlreadyExistMemberException.class)
	public ResponseEntity<ErrorResponse> AlreadyExistMemberException(AlreadyExistMemberException e) {
		return ResponseEntity.badRequest().body(new ErrorResponse(e.getMsg(), e.getField()));
	}

	@ExceptionHandler(InvalidMemberException.class)
	public ResponseEntity<ErrorResponse> AlreadyExistMemberException(InvalidMemberException e) {
		return ResponseEntity.badRequest().body(new ErrorResponse(e.getMsg(), e.getField()));
	}

	@ExceptionHandler(MemberNotFoundException.class)
	public ResponseEntity<ErrorResponse> MemberNotFoundException(MemberNotFoundException e) {
		return ResponseEntity.badRequest().body(new ErrorResponse(e.getMsg(), e.getField()));
	}

	@ExceptionHandler(InvalidAuthKeyException.class)
	public ResponseEntity<ErrorResponse> InvalidAuthKeyException(InvalidAuthKeyException e) {
		return ResponseEntity.badRequest().body(new ErrorResponse(e.getMsg(), e.getField()));
	}
	
}
