package com.homecookingshare.member.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.homecookingshare.common.ErrorResponse;
import com.homecookingshare.member.aggregate.exception.AlreadyExistMemberException;
import com.homecookingshare.member.aggregate.exception.InvalidMemberException;
import com.homecookingshare.member.aggregate.exception.MemberNotFoundException;

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

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorResponse> IllegalArgumentException(IllegalArgumentException e) {
		return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage(), null));
	}
}
