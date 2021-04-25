package com.homecookingshare.common;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommonErrorContoller {
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorResponse> HttpMessageNotReadableException(HttpMessageNotReadableException e) {
		return ResponseEntity.badRequest().body(new ErrorResponse("값을 형식에 맞게 입력해주세요.", null));
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ErrorResponse> HttpRequestMethodNotSupportedException(
			HttpRequestMethodNotSupportedException e) {
		return ResponseEntity.notFound().build();
	}
}
