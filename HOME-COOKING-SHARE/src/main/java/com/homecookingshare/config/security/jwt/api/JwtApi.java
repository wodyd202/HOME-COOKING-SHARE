package com.homecookingshare.config.security.jwt.api;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.homecookingshare.config.security.MemberPrincipal;
import com.homecookingshare.config.security.jwt.JwtTokenProvider;
import com.homecookingshare.config.security.jwt.TokenStore;
import com.homecookingshare.config.security.jwt.exception.InvalidAccessTokenException;
import com.homecookingshare.config.security.jwt.exception.InvalidRefreshTokenException;
import com.homecookingshare.domain.member.MemberRule;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("oauth")
@RequiredArgsConstructor
@Api(tags = "사용자 인증 API")
public class JwtApi {
	private final UserDetailsService userService;
	private final JwtTokenProvider jwtTokenProvider;
	private final PasswordEncoder passwordEncoder;
	private final TokenStore tokenStore;

	@ApiOperation("jwt 토큰 재발급")
	@PostMapping("refresh-token")
	public ResponseEntity<JwtToken> refreshToken(
			RefreshTokenDto refreshToken
		) {
		validationRefreshToken(refreshToken);
		JwtToken jwtToken = tokenStore.findByUser(refreshToken.getEmail())
				.orElseThrow(() -> new InvalidRefreshTokenException("토큰 정보가 존재하지 않습니다."));
		if(!compareTo(refreshToken, jwtToken)) {
			throw new InvalidRefreshTokenException("토큰 정보가 일치하지 않습니다. 다시 확인해주세요.");
		}
		MemberPrincipal loginUser = (MemberPrincipal) userService.loadUserByUsername(refreshToken.getEmail());
		return new ResponseEntity<>(createToken(loginUser), HttpStatus.OK);
	}
	
	private boolean compareTo(RefreshTokenDto refreshToken, JwtToken jwtToken) {
		return jwtToken.equalRefreshToken(refreshToken.getRefreshToken());
	}

	@ApiOperation("jwt 토큰 발급")
	@PostMapping("token")
	public ResponseEntity<JwtToken> oauthToken(
			AccessTokenDto loginInfo
		) {
		validationAccessToken(loginInfo);
		MemberPrincipal loginUser = (MemberPrincipal) userService.loadUserByUsername(loginInfo.getEmail());
		if (!matchPassword(loginInfo, loginUser)) {
			throw new InvalidAccessTokenException("이메일 혹은 비밀번호가 일치하지 않습니다.");
		} 
		if(!loginUser.isAccountNonLocked()) {
			throw new InvalidAccessTokenException("인증이 필요한 계정입니다.");
		}
		return new ResponseEntity<>(createToken(loginUser), HttpStatus.OK);
	}

	private void validationAccessToken(AccessTokenDto loginInfo) {
		assertNotEmptyStringValue(loginInfo.getEmail(), new InvalidRefreshTokenException("이메일 정보를 입력해주세요."));
		assertNotEmptyStringValue(loginInfo.getPassword(), new InvalidRefreshTokenException("비밀번호를 입력해주세요."));
	}

	private void validationRefreshToken(RefreshTokenDto refreshToken) {
		if (refreshToken == null) {
			throw new InvalidRefreshTokenException("refreshToken 정보를 입력해주세요.");
		}
		assertNotEmptyStringValue(refreshToken.getEmail(), new InvalidRefreshTokenException("이메일 정보를 입력해주세요."));
		assertNotEmptyStringValue(refreshToken.getRefreshToken(),new InvalidRefreshTokenException("refreshToken 정보를 입력해주세요."));
	}
	
	private void assertNotEmptyStringValue(String value, IllegalArgumentException e) {
		if(value == null || value.isEmpty()) {
			throw e;
		}
	}

	private boolean matchPassword(AccessTokenDto loginInfo, MemberPrincipal loginUser) {
		return loginUser != null && passwordEncoder.matches(loginInfo.getPassword(), loginUser.getPassword());
	}

	private JwtToken createToken(MemberPrincipal loginUser) {
		return jwtTokenProvider.provideToken(loginUser.getUsername(), loginUser.getAuthorities().stream()
				.map(c -> new String(c.getAuthority())).collect(Collectors.toList()));
	}
	
	public JwtToken accessTokenWithoutPassword(
			AccessTokenDto loginInfo
		){
		return jwtTokenProvider.provideToken(loginInfo.getEmail(), Arrays.asList(MemberRule.MEMBER.toString()));
	}
}
