package com.homecookingshare.command.member.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.homecookingshare.command.member.infra.validator.ChangePasswordValidator;
import com.homecookingshare.command.member.infra.validator.ImageFileValidator;
import com.homecookingshare.command.member.infra.validator.RegisterMemberValidator;
import com.homecookingshare.command.member.model.MemberCommand;
import com.homecookingshare.command.member.service.MemberService;
import com.homecookingshare.common.fileUpload.FileUploader;
import com.homecookingshare.config.security.LoginUser;
import com.homecookingshare.domain.member.Member;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "사용자 상태 관련 API")
@RestController
@RequestMapping("api/v1/member")
@AllArgsConstructor
public class MemberCommandApi {
	private MemberService memberService;
	
	private ChangePasswordValidator changePasswordValidator;
	private ImageFileValidator imageFileValidator;
	private RegisterMemberValidator registerMemberValidator;
	
	private FileUploader fileUploader;
	
	@ApiOperation("회원가입")
	@PostMapping
	public ResponseEntity<Member> execute(
			@RequestBody MemberCommand.RegisterMember command
		){
		Member create = memberService.create(registerMemberValidator, command);
		return new ResponseEntity<>(create, HttpStatus.CREATED);
	}
	
	@ApiOperation("비밀번호 변경")
	@PutMapping("password")
	public ResponseEntity<Member> execute(
			@RequestBody MemberCommand.ChangePassword command,
			@ApiIgnore
			@LoginUser com.homecookingshare.domain.member.read.Member loginMember
		){
			Member changePassword = memberService.changePassword(changePasswordValidator, loginMember.getEmail(), command);
			return new ResponseEntity<>(changePassword, HttpStatus.OK);
	}
	
	@ApiOperation("사용자 프로필 이미지 변경")
	@PutMapping("image")
	public ResponseEntity<Member> execute(
			@RequestBody MemberCommand.ChangeImage command,
			@ApiIgnore
			@LoginUser com.homecookingshare.domain.member.read.Member loginMember
		){
		Member changeImage = memberService.changeImage(fileUploader, imageFileValidator, loginMember.getEmail(), command);
		return new ResponseEntity<>(changeImage, HttpStatus.OK);
	}
}
