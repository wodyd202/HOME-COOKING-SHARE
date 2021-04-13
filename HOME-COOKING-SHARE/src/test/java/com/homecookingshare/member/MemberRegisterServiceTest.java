package com.homecookingshare.member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.homecookingshare.member.domain.Email;
import com.homecookingshare.member.domain.Password;
import com.homecookingshare.member.domain.aggregate.Member;
import com.homecookingshare.member.domain.aggregate.Member.MemberRole;
import com.homecookingshare.member.domain.aggregate.Member.MemberState;
import com.homecookingshare.member.domain.aggregate.exception.InvalidMemberException;
import com.homecookingshare.member.service.register.MemberRegisterService;
import com.homecookingshare.member.service.register.RegisterMember;
import com.homecookingshare.member.service.register.RegisterMemberValidator;

public class MemberRegisterServiceTest extends MemberTest{
	
	@BeforeEach
	void setUp() {
		registerMemberValidator = new RegisterMemberValidator();
		memberRepository = new FakeMemberRepository();
		memberRegisterService = new MemberRegisterService(registerMemberValidator, memberRepository);
	}
	
	@Test
	@DisplayName("이메일 정규식이 일치하지 않을 때_에러")
	void register_3() {
		RegisterMember registerMember = mock(RegisterMember.class);
		when(registerMember.getEmail()).thenReturn("wodyd202naver.com");
		when(registerMember.getNickName()).thenReturn("닉네임");
		when(registerMember.getPassword()).thenReturn("password");
		
		assertThrows(InvalidMemberException.class, ()->{
			memberRegisterService.register(registerMember);
		});
	}
	
	@Test
	@DisplayName("빈값이 존재할 때_에러")
	void register_2() {
		RegisterMember registerMember = mock(RegisterMember.class);
		when(registerMember.getEmail()).thenReturn("wodyd202naver.com");
		when(registerMember.getNickName()).thenReturn("닉네임");
		when(registerMember.getPassword()).thenReturn("password");
		
		assertThrows(InvalidMemberException.class, ()->{
			when(registerMember.getEmail()).thenReturn(null);
			memberRegisterService.register(registerMember);
		});
		assertThrows(InvalidMemberException.class, ()->{
			when(registerMember.getNickName()).thenReturn(null);
			memberRegisterService.register(registerMember);
		});
		assertThrows(InvalidMemberException.class, ()->{
			when(registerMember.getPassword()).thenReturn(null);
			memberRegisterService.register(registerMember);
		});
	}
	
	@Test
	@DisplayName("정상 등록 케이스")
	void register_1() {
		RegisterMember registerMember = RegisterMember.builder()
				.email("wodyd202@naver.com")
				.nickName("닉네임")
				.password("password")
				.build();
		memberRegisterService.register(registerMember);
		Optional<Member> findMember = memberRepository.findByEmail(new Email("wodyd202@naver.com"));
		if(!findMember.isPresent()) {
			fail();
		}
		Member member = findMember.get();
		assertThat(member.getEmail()).isEqualTo(new Email("wodyd202@naver.com"));
		assertThat(member.getPassword()).isEqualTo(new Password("password"));
		assertThat(member.getRole()).isEqualTo(MemberRole.MEMBER);
		assertThat(member.getState()).isEqualTo(MemberState.CREATE);
	}
	
}
