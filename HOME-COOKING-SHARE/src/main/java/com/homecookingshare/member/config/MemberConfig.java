package com.homecookingshare.member.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.homecookingshare.member.infrastructure.SimpleMemberRepository;
import com.homecookingshare.member.service.register.MemberRegisterService;
import com.homecookingshare.member.service.register.RegisterMemberValidator;
import com.homecookingshare.member.service.update.MemberUpdateService;
import com.homecookingshare.member.service.update.UpdateMemberValidator;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class MemberConfig {
	
	private final SimpleMemberRepository memberRepository;
	
	@Bean
	public MemberUpdateService memberUpdateService() { 
		return new MemberUpdateService(updateMemberValidator(), memberRepository);
	}
	
	@Bean
	public MemberRegisterService memberRegisterService() { 
		return new MemberRegisterService(registerMemberValidator(), memberRepository);
	}
	
	@Bean
	public UpdateMemberValidator updateMemberValidator() {
		return new UpdateMemberValidator();
	}
	
	@Bean
	public RegisterMemberValidator registerMemberValidator() {
		return new RegisterMemberValidator();
	}
}
