package com.homecookingshare.member;

import com.homecookingshare.common.Validator;
import com.homecookingshare.member.infrastructure.MemberRepository;
import com.homecookingshare.member.service.register.MemberRegisterService;
import com.homecookingshare.member.service.register.RegisterMember;

public class MemberTest {
	MemberRepository memberRepository;
	Validator<RegisterMember> registerMemberValidator;
	MemberRegisterService memberRegisterService;
	
	public void registerMember(RegisterMember registerMember) {
		memberRegisterService.register(registerMember);
	}
}
