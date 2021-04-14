package com.homecookingshare.member.integrationTest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.homecookingshare.member.MemberTest;
import com.homecookingshare.member.service.register.MemberRegisterService;
import com.homecookingshare.member.service.register.RegisterMember;

@SpringBootTest
public class MemberRegisterServiceTest extends MemberTest{
	
	@Autowired
	private MemberRegisterService service;
	
	@Test
	@DisplayName("정상 등록 케이스")
	void register_1() {
		RegisterMember registerMember = RegisterMember.builder()
				.email("wodyd202@naver.com")
				.nickName("닉네임")
				.password("password")
				.build();
		service.register(registerMember);
	}
	
}
