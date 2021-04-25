package com.homecookingshare.runner;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.homecookingshare.command.member.service.register.RegisterMemberCommand;
import com.homecookingshare.command.member.service.register.RegisterMemberService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AppRunner implements ApplicationRunner{

	private final RegisterMemberService registerMemberService; 
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
//		RegisterMemberCommand command = RegisterMemberCommand.builder()
//				.email("wodyd202@naver.com")
//				.nickName("wodyd")
//				.password("password").build();
//		registerMemberService.register(command);
	}

}
