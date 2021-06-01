package com.homecookingshare.config.security.jwt.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.homecookingshare.common.ErrorResponse;
import com.homecookingshare.config.security.jwt.exception.InvalidAccessTokenException;
import com.homecookingshare.config.security.jwt.exception.InvalidRefreshTokenException;

@RestControllerAdvice
public class JwtErrorController {

	@ExceptionHandler(InvalidAccessTokenException.class)
	public ResponseEntity<ErrorResponse> InvalidAccessTokenException(InvalidAccessTokenException e) {
		return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InvalidRefreshTokenException.class)
	public ResponseEntity<ErrorResponse> InvalidRefreshTokenException(InvalidRefreshTokenException e) {
		return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
	}
}
