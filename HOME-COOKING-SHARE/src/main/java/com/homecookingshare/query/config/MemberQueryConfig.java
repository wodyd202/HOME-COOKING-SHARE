package com.homecookingshare.query.config;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.homecookingshare.command.member.service.update.MemberAuthHandleService;
import com.homecookingshare.common.mail.EmailUtilImpl;
import com.homecookingshare.query.eventHandler.AuthMailSender;
import com.homecookingshare.query.eventHandler.MemberImageChangeEventHandler;
import com.homecookingshare.query.eventHandler.RegisterMemberEventHandler;
import com.homecookingshare.query.infrastructure.SimpleMemberRedisRepository;
import com.homecookingshare.query.service.MemberLoadService;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class MemberQueryConfig {
	private final ApplicationEventPublisher publisher;
	private final EmailUtilImpl emailUtilImpl;

	@Bean
	public SimpleMemberRedisRepository memberRedisRepository() {
		return new SimpleMemberRedisRepository();
	}

	@Bean
	public MemberLoadService memberLoadService() {
		return new MemberLoadService(memberRedisRepository());
	}

	@Bean
	public RegisterMemberEventHandler registerMemberEventHandler() {
		return new RegisterMemberEventHandler(memberRedisRepository());
	}

	@Bean
	public AuthMailSender authMailSender() {
		return new AuthMailSender(emailUtilImpl, memberRedisRepository());
	}
	
	@Bean
	public MemberAuthHandleService authHandleService() {
		return new MemberAuthHandleService(publisher, memberRedisRepository(), authMailSender());
	}
	
	@Bean
	public MemberImageChangeEventHandler memberImageChangeEventHandler() {
		return new MemberImageChangeEventHandler(memberRedisRepository());
	}
}
