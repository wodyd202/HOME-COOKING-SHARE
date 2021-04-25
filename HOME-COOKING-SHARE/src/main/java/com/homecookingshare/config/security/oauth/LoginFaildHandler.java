package com.homecookingshare.config.security.oauth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;

public class LoginFaildHandler implements WebResponseExceptionTranslator<OAuth2Exception> {

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
		String message = e.getMessage();
		Map<String, String> responseMap = new HashMap<>();
		if(message != null && message.equals("User is disabled")) {
			responseMap.put("message", "이메일 인증이 필요한 계정입니다.");
		}else {
			responseMap.put("message", "사용자 이메일 혹은 비밀번호가 일치하지 않습니다.");
		}
		return new ResponseEntity(responseMap, HttpStatus.UNAUTHORIZED);
	}
	
}
