package com.homecookingshare.command.memberEmailAuth.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.homecookingshare.command.memberEmailAuth.infra.validator.EmailAuthenticationValidator;
import com.homecookingshare.command.memberEmailAuth.model.MemberEmailAuthCommand;
import com.homecookingshare.command.memberEmailAuth.service.MemberEmailAuthService;
import com.homecookingshare.config.security.jwt.api.AccessTokenDto;
import com.homecookingshare.config.security.jwt.api.JwtApi;
import com.homecookingshare.config.security.jwt.api.JwtToken;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

@Api(tags = "사용자 이메일 인증 관련 API")
@RestController
@RequestMapping("api/v1/member/auth")
@AllArgsConstructor
public class MemberEmailAuthCommandApi {
	private MemberEmailAuthService memberEmailAuthService;
	private EmailAuthenticationValidator authenticationValidator;
	private JwtApi jwtApi;
	
	@ApiOperation("회원 이메일 인증")
	@PostMapping
	public ResponseEntity<JwtToken> execute(
			@RequestBody MemberEmailAuthCommand.Verifycation command
		){
		memberEmailAuthService.verifycation(authenticationValidator, command);
		AccessTokenDto loginInfo = new AccessTokenDto(command.getRequireVerificationTargetEmail());
		return new ResponseEntity<>(jwtApi.accessTokenWithoutPassword(loginInfo), HttpStatus.OK);
	}
}
