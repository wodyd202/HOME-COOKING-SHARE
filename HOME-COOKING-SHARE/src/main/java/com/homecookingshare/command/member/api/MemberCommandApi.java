package com.homecookingshare.command.member.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.homecookingshare.command.member.infra.validator.ChangePasswordValidator;
import com.homecookingshare.command.member.infra.validator.ImageFileValidator;
import com.homecookingshare.command.member.infra.validator.RegisterMemberValidator;
import com.homecookingshare.command.member.model.MemberCommand;
import com.homecookingshare.command.member.service.MemberService;
import com.homecookingshare.domain.member.Member;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/v1/member")
@AllArgsConstructor
public class MemberCommandApi {
	private MemberService memberService;
	
	private ChangePasswordValidator changePasswordValidator;
	private ImageFileValidator imageFileValidator;
	private RegisterMemberValidator registerMemberValidator;
	
	@PostMapping
	public ResponseEntity<Member> execute(
			MemberCommand.RegisterMemberCommand command
		){
		Member create = memberService.create(registerMemberValidator, command);
		return new ResponseEntity<>(create, HttpStatus.CREATED);
	}
}
