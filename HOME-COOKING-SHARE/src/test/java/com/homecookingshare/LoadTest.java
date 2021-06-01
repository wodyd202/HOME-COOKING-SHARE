package com.homecookingshare;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.homecookingshare.domain.member.AuthType;
import com.homecookingshare.domain.member.Email;
import com.homecookingshare.domain.member.MemberRule;
import com.homecookingshare.domain.member.MemberState;
import com.homecookingshare.domain.member.Password;
import com.homecookingshare.domain.member.Profile;
import com.homecookingshare.domain.member.read.Member;
import com.homecookingshare.query.member.infra.MemberReadRepository;

@SpringBootTest
public class LoadTest {
	
	@Autowired
	private MemberReadRepository memberReadRepository;
	
	@Test
	void load() {
		Member member = Member.builder()
				.email(new Email("test@naver.com"))
				.password(new Password("password"))
				.createDateTime(new Date())
				.profile(new Profile("닉네임"))
				.rule(MemberRule.MEMBER)
				.state(MemberState.CREATE)
				.authType(AuthType.NO)
				.build();
		memberReadRepository.save(member);
		
//		memberReadRepository.findLoginInfoByEmail(new Email("test@naver.com"));
		
		memberReadRepository.findAll();
	}
}
