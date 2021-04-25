package com.homecookingshare.command.member.config;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.homecookingshare.command.member.eventHandler.MemberAuthSuccessEventHandler;
import com.homecookingshare.command.member.service.register.RegisterMemberCommandValidator;
import com.homecookingshare.command.member.service.register.RegisterMemberService;
import com.homecookingshare.command.member.service.update.MemberImageChangeService;
import com.homecookingshare.command.member.service.update.MemberImageChangeValidator;
import com.homecookingshare.common.fileUpload.FileUploadService;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class MemberConfig {
	private final ApplicationEventPublisher publisher;
	private final PasswordEncoder passwordEncoder;
	private final SimpleMemberRepository memberRepository;
	private final FileUploadService fileUploadService;
	
	@Bean
	public RegisterMemberCommandValidator validate() {
		return new RegisterMemberCommandValidator();
	}
	
	@Bean
	public RegisterMemberService registerMemberService() {
		return new RegisterMemberService(publisher, validate(), memberRepository, passwordEncoder);
	}
	
	@Bean
	public MemberAuthSuccessEventHandler memberAuthSuccessEventHandler() {
		return new MemberAuthSuccessEventHandler(memberRepository);
	}
	
	@Bean
	public MemberImageChangeValidator memberImageChangeValidator() {
		return new MemberImageChangeValidator();
	}
	
	@Bean
	public MemberImageChangeService memberImageChangeService() {
		return new MemberImageChangeService(publisher, memberImageChangeValidator(), memberRepository, fileUploadService);
	}
}
