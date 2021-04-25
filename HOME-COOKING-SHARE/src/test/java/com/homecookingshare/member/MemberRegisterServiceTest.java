package com.homecookingshare.member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

import com.homecookingshare.MockApplicationEventPublisher;
import com.homecookingshare.command.member.entity.AuthKey;
import com.homecookingshare.command.member.entity.Member;
import com.homecookingshare.command.member.exception.AlreadyExistMemberException;
import com.homecookingshare.command.member.exception.InvalidMemberException;
import com.homecookingshare.command.member.infrastructure.MemberRepository;
import com.homecookingshare.command.member.service.register.RegisterMemberCommand;
import com.homecookingshare.command.member.service.register.RegisterMemberCommandValidator;
import com.homecookingshare.command.member.service.register.RegisterMemberService;
import com.homecookingshare.command.member.values.AuthType;
import com.homecookingshare.command.member.values.Email;
import com.homecookingshare.common.Validator;
import com.homecookingshare.common.mail.EmailUtilImpl;
import com.homecookingshare.member.fake.FakeMemberQueryRepository;
import com.homecookingshare.member.fake.FakeMemberRepository;
import com.homecookingshare.query.eventHandler.AuthMailSender;
import com.homecookingshare.query.eventHandler.RegisterMemberEventHandler;
import com.homecookingshare.query.infrastructure.MemberQueryRepository;
import com.homecookingshare.query.projections.QueryMember;

public class MemberRegisterServiceTest {
	Validator<RegisterMemberCommand> validator;
	MemberRepository memberRepository;
	RegisterMemberService service;
	MemberQueryRepository memberQueryRepository;
	RegisterMemberEventHandler registerMemberEventHandler;	
	AuthMailSender authMailSender;
	
	@BeforeEach
	void setUp() {
		validator = new RegisterMemberCommandValidator();
		memberRepository = new FakeMemberRepository();
		memberQueryRepository = new FakeMemberQueryRepository();
		registerMemberEventHandler = new RegisterMemberEventHandler(memberQueryRepository);
		authMailSender = new AuthMailSender(mock(EmailUtilImpl.class), memberQueryRepository);
		
		service = new RegisterMemberService(new MockApplicationEventPublisher(registerMemberEventHandler, authMailSender), 
											validator, 
											memberRepository, 
											PasswordEncoderFactories.createDelegatingPasswordEncoder());
	}

	@Test
	@DisplayName("하루동안 이메일 인증을 하지 않고 다시 재가입하는 테스트")
	void test_10() {
		assertThrows(AlreadyExistMemberException.class, ()->{
			Member member = mock(Member.class);
			when(member.getEmail()).thenReturn(new Email("test@naver.com"));
			when(member.getCreateDateTime()).thenReturn(new Date(new Date().getTime() - 86400000 + 1000000));
			when(member.getAuthType()).thenReturn(AuthType.NO);
			memberRepository.save(member);
			RegisterMemberCommand command = RegisterMemberCommand.builder()
					.email("test@naver.com")
					.nickName("wodyd")
					.password("password").build();
			service.register(command);
			assertThat(member.getCreateDateTime()).isEqualTo(new Date());
		});
	}
	
	@Test
	@DisplayName("중복 가입된 사용자가 있지만, 이메일 인증을 하지 않은 사용자일 경우 에러")
	void test_9() {
		assertThrows(AlreadyExistMemberException.class, ()->{
			RegisterMemberCommand command = RegisterMemberCommand.builder()
					.email("wodyd202@naver.com")
					.nickName("wodyd")
					.password("password").build();
			memberRepository.save(new Member(command));
			service.register(command);
		});
	}
	
	@Test
	@DisplayName("사용자 생성 후 query 서비스에 추가 테스트")
	void test_8() {
		RegisterMemberCommand command = RegisterMemberCommand.builder()
				.email("wodyd202@naver.com")
				.nickName("wodyd")
				.password("password").build();
		service.register(command);
		Optional<QueryMember> findByEmail = memberQueryRepository.findByEmail("wodyd202@naver.com");
		Optional<AuthKey> findAuthKey = memberQueryRepository.findAuthKey("wodyd202@naver.com");
		assertThat(findByEmail.isPresent()).isTrue();
		assertThat(findAuthKey.isPresent()).isTrue();
	}
	
	@Test
	@DisplayName("비밀번호 엔코딩 테스트")
	void test_6() {
		RegisterMemberCommand command = RegisterMemberCommand.builder()
				.email("wodyd202@naver.com")
				.nickName("wodyd")
				.password("password").build();
		RegisterMemberCommand register = service.register(command);
		assertThat(PasswordEncoderFactories.createDelegatingPasswordEncoder().matches("password", register.getPassword()));
	}
	
	@Test
	@DisplayName("닉네임을 입력하지 않았을 때 에러 테스트")
	void test_5() {
		assertThrows(InvalidMemberException.class, ()->{
			RegisterMemberCommand command = RegisterMemberCommand.builder()
					.email("wodyd202@naver.com")
					.nickName("")
					.password("password").build();
			service.register(command);
		});
	}

	@Test
	@DisplayName("비밀번호를 입력하지 않았을 때 에러 테스트")
	void test_4() {
		assertThrows(InvalidMemberException.class, ()->{
			RegisterMemberCommand command = RegisterMemberCommand.builder()
					.email("wodyd202@naver.com")
					.nickName("wodyd")
					.password("").build();
			service.register(command);
		});
	}
	
	@Test
	@DisplayName("이메일을 입력하지 않았을 때 에러 테스트")
	void test_3() {
		assertThrows(InvalidMemberException.class, ()->{
			RegisterMemberCommand command = RegisterMemberCommand.builder()
					.email("")
					.nickName("wodyd")
					.password("password").build();
			service.register(command);
		});
	}
	
	@Test
	@DisplayName("중복된 이메일을 입력했을 때 에러 테스트")
	void test_2() {
		assertThrows(AlreadyExistMemberException.class, ()->{
			RegisterMemberCommand command = RegisterMemberCommand.builder()
					.email("wodyd202@naver.com")
					.nickName("wodyd")
					.password("password").build();
			Member member = new Member(command);
			member.authSuccess();
			memberRepository.save(member);
			service.register(command);
		});
	}
	
	@Test
	@DisplayName("사용자 생성 테스트")
	void test_1() {
		RegisterMemberCommand command = RegisterMemberCommand.builder()
				.email("wodyd202@naver.com")
				.nickName("wodyd")
				.password("password").build();
		service.register(command);
		Optional<Member> findMember = memberRepository.findByEmail(new Email("wodyd202@naver.com"));
		assertThat(findMember.isPresent()).isTrue();
	}
}
