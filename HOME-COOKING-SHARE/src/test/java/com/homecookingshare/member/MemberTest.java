package com.homecookingshare.member;

import org.junit.jupiter.api.BeforeEach;

import com.homecookingshare.common.Validator;
import com.homecookingshare.member.infrastructure.MemberRepository;
import com.homecookingshare.member.service.register.MemberRegisterService;
import com.homecookingshare.member.service.register.RegisterMember;
import com.homecookingshare.member.service.register.RegisterMemberValidator;
import com.homecookingshare.member.service.update.ChangeImageMember;
import com.homecookingshare.member.service.update.MemberUpdateService;
import com.homecookingshare.member.service.update.UpdateMemberValidator;

public class MemberTest {
	Validator<RegisterMember> registerMemberValidator;
	Validator<ChangeImageMember> updateMemberValidator;

	MemberRegisterService memberRegisterService;
	MemberUpdateService memberUpdateService;
	
	MemberRepository memberRepository;
	
	@BeforeEach
	void setUp() {
		registerMemberValidator = new RegisterMemberValidator();
		memberRepository = new FakeMemberRepository();
		memberRegisterService = new MemberRegisterService(registerMemberValidator, memberRepository);
		updateMemberValidator = new UpdateMemberValidator();
		memberUpdateService = new MemberUpdateService(updateMemberValidator,memberRepository);
	}
	
	public void registerMember(RegisterMember registerMember) {
		memberRegisterService.register(registerMember);
	}
}
