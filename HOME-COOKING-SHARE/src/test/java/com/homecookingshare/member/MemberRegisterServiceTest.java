package com.homecookingshare.member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.homecookingshare.common.Validator;
import com.homecookingshare.member.aggregate.Member;
import com.homecookingshare.member.aggregate.event.MemberEvent;
import com.homecookingshare.member.aggregate.exception.InvalidMemberException;
import com.homecookingshare.member.infrastructure.MemberEventRepository;
import com.homecookingshare.member.infrastructure.MemberQueryRepository;
import com.homecookingshare.member.service.eventListener.MemberCommandListener;
import com.homecookingshare.member.service.eventListener.MemberQueryListener;
import com.homecookingshare.member.service.register.RegisterMember;
import com.homecookingshare.member.service.register.RegisterMemberValidator;

public class MemberRegisterServiceTest {

	Validator<RegisterMember> memberRegisterValidator;
	
	MemberQueryRepository memberQueryRepository;
	MemberQueryListener memberQueryListener;
	
	MemberEventRepository memberEventRepository;
	MemberCommandListener memberCommandListener;
	
	MemberRegisterService memberRegisterService;
	
	@BeforeEach
	void setUp() {
		memberRegisterValidator = new RegisterMemberValidator();
		
		memberQueryRepository = new FakeMemberQueryRepository();
		memberQueryListener = new MemberQueryListener(memberQueryRepository);
		
		memberEventRepository = new FakeMemberEventRepository();
		memberCommandListener = new MemberCommandListener(memberEventRepository);
		
		memberRegisterService = new MemberRegisterService(memberRegisterValidator,memberQueryRepository,memberQueryListener,memberCommandListener);
	}

	@Test
	@DisplayName("닉네임이 8자 이상 넘었을 때_에러")
	void test_6() {
		assertThrows(InvalidMemberException.class, ()->{
			RegisterMember registerMember = RegisterMember.builder()
					.email("wodyd202@naver.com")
					.password("password")
					.nickName("닉네임닉네임닉네임닉네임닉네임닉네임닉네임닉네임닉네임닉네임닉네임닉네임")
					.build();
			
			memberRegisterService.register(registerMember);
		});
	}

	@Test
	@DisplayName("닉네임이 존재하지 않을 때_에러")
	void test_5() {
		assertThrows(InvalidMemberException.class, ()->{
			RegisterMember registerMember = RegisterMember.builder()
					.email("wodyd202@naver.com")
					.password("password")
					.build();
			
			memberRegisterService.register(registerMember);
		});
	}
	
	@Test
	@DisplayName("이메일 형식이 올바르지 않을 때_에러")
	void test_4() {
		assertThrows(InvalidMemberException.class, ()->{
			RegisterMember registerMember = RegisterMember.builder()
					.email("이메일 에러")
					.nickName("닉네임")
					.password("password")
					.build();
			
			memberRegisterService.register(registerMember);
		});
	}
	
	@Test
	@DisplayName("이메일이 100자 이상 넘어갔을 때_에러")
	void test_3() {
		assertThrows(InvalidMemberException.class, ()->{
			RegisterMember registerMember = RegisterMember.builder()
					.email("wodyd202wodyd202wodyd202wodyd202wodyd202wodyd202wodyd202wodyd202wodyd202wodyd202wodyd202wodyd202wodyd202wodyd202wodyd202wodyd202wodyd202wodyd202wodyd202wodyd202wodyd202wodyd202wodyd202wodyd202wodyd202wodyd202wodyd202wodyd202wodyd202@naver.com")
					.nickName("닉네임")
					.password("password")
					.build();
			
			memberRegisterService.register(registerMember);
		});
	}
	
	@Test
	@DisplayName("이메일이 비워져 있을 때_에러")
	void test_2() {
		assertThrows(InvalidMemberException.class, ()->{
			RegisterMember registerMember = RegisterMember.builder()
					.nickName("닉네임")
					.password("password")
					.build();
			
			memberRegisterService.register(registerMember);
		});
	}
	
	@Test
	@DisplayName("정상 등록 케이스")
	void test_1() {
		RegisterMember registerMember = RegisterMember.builder()
				.email("wodyd202@naver.com")
				.nickName("닉네임")
				.password("password")
				.build();
		
		memberRegisterService.register(registerMember);
		
		List<MemberEvent> eventList = memberEventRepository.findByEmail(new Email("wodyd202@naver.com"));
		Optional<Member> findByEmail = memberQueryRepository.findByEmail(new Email("wodyd202@naver.com"));
		
		if(!findByEmail.isPresent()) {
			fail();
		}
		
		Member member = findByEmail.get();
		assertThat(eventList.size()).isEqualTo(1);
		assertThat(member.getEmail()).isEqualTo(new Email("wodyd202@naver.com"));
		assertThat(member.getPassword()).isEqualTo(new Password("password"));
		assertThat(member.getProfile().getNickName()).isEqualTo("닉네임");
	}
}
