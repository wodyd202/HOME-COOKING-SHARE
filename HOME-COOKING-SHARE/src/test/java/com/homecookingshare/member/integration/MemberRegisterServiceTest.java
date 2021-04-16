package com.homecookingshare.member.integration;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import com.homecookingshare.member.service.load.MemberLoadService;
import com.homecookingshare.member.service.register.MemberRegisterService;
import com.homecookingshare.member.service.register.RegisterMember;

@SpringBootTest
public class MemberRegisterServiceTest {
	
	@Autowired
	private MemberRegisterService memberRegisterService;

	@Autowired
	private MemberLoadService memberLoadService;
	
	@Test
	void test() {
		RegisterMember registerMember = RegisterMember.builder()
				.email("wodyd202@naver.com")
				.nickName("닉네임")
				.password("password")
				.build();
		
		memberRegisterService.register(registerMember);
		UserDetails loadUserByUsername = memberLoadService.loadUserByUsername("wodyd202@naver.com");
		assertThat(loadUserByUsername).isNotNull();
	}
}
