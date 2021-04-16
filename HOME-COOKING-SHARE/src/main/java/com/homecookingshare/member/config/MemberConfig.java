package com.homecookingshare.member.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.homecookingshare.common.fileUpload.FileUploadService;
import com.homecookingshare.member.infrastructure.SimpleMemberRedisRepository;
import com.homecookingshare.member.service.load.MemberLoadService;
import com.homecookingshare.member.service.register.MemberRegisterService;
import com.homecookingshare.member.service.register.RegisterMemberValidator;
import com.homecookingshare.member.service.update.ChangeMemberImageValidator;
import com.homecookingshare.member.service.update.MemberChangeImageService;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class MemberConfig {

	private final SimpleMemberRedisRepository memberRedisRepository;
	private final FileUploadService fileUploadService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Bean
	public ChangeMemberImageValidator changeMemberImageValidator() {
		return new ChangeMemberImageValidator();
	}
	
	@Bean
	public MemberChangeImageService memberChangeImageService() {
		return new MemberChangeImageService(changeMemberImageValidator(), memberRedisRepository, fileUploadService, publisher);
	}

	@Bean
	public MemberLoadService memberLoadService() {
		return new MemberLoadService(memberRedisRepository);
	}

	@Bean
	public MemberRegisterService memberRegisterService() {
		return new MemberRegisterService(memberValidator(), memberRedisRepository, passwordEncoder);
	}

	@Bean
	public RegisterMemberValidator memberValidator() {
		return new RegisterMemberValidator();
	}
}
