package com.homecookingshare.command.member.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.homecookingshare.command.member.entity.AuthKey;
import com.homecookingshare.command.member.service.update.MemberAuthHandleService;
import com.homecookingshare.command.member.values.Email;
import com.homecookingshare.query.eventHandler.AuthMailSender;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/member/auth")
@Api(tags = "사용자 인증 API")
public class MemberAuthAPI {
	private final MemberAuthHandleService memberAuthHandleService;
	private final AuthMailSender authMailSender; 
	
	@GetMapping
	@ApiOperation("사용자 인증 이메일 발급")
	public ResponseEntity<String> getAuthMail(
			@RequestBody 
			@ApiParam(name = "email" ,required = true, value = "이메일")
			Email email){
		authMailSender.sendAuthMail(AuthKey.createAuthKey(email), true);
		return new ResponseEntity<>("인증 번호가 정상적으로 발송되었습니다.", HttpStatus.OK);
	}
	
	@PostMapping
	@ApiOperation("사용자 인증")
	public ResponseEntity<String> authProcess(
			@RequestBody
			@ApiParam(name = "authKey" ,required = true, value = "인증 정보")
			AuthKey authKey
		){
		memberAuthHandleService.matchAuthKey(authKey);
		return new ResponseEntity<>("인증이 정상적으로 완료되었습니다.", HttpStatus.OK);
	}
}
