package com.homecookingshare.member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationListener;

import com.homecookingshare.MockApplicationEventPublisher;
import com.homecookingshare.command.member.entity.AuthKey;
import com.homecookingshare.command.member.entity.Member;
import com.homecookingshare.command.member.eventHandler.MemberAuthSuccessEventHandler;
import com.homecookingshare.command.member.infrastructure.MemberRepository;
import com.homecookingshare.command.member.service.register.RegisterMemberCommand;
import com.homecookingshare.command.member.service.update.MemberAuthHandleService;
import com.homecookingshare.command.member.values.AuthType;
import com.homecookingshare.command.member.values.Email;
import com.homecookingshare.common.mail.EmailUtilImpl;
import com.homecookingshare.member.fake.FakeMemberQueryRepository;
import com.homecookingshare.member.fake.FakeMemberRepository;
import com.homecookingshare.query.eventHandler.AuthMailSender;
import com.homecookingshare.query.infrastructure.MemberQueryRepository;
import com.homecookingshare.query.projections.QueryMember;

public class MemberAuthHandleServiceTest {
	
	MemberQueryRepository memberQueryRepository;
	EmailUtilImpl emailUtil;
	AuthMailSender authMailSender;
	MemberAuthHandleService memberAuthHandleService;
	MemberRepository memberRepository;
	
	@BeforeEach
	void setUp() {
		memberRepository = new FakeMemberRepository();
		memberQueryRepository = new FakeMemberQueryRepository();
		emailUtil = mock(EmailUtilImpl.class);
		authMailSender = new AuthMailSender(emailUtil, memberQueryRepository);
		
		RegisterMemberCommand command = RegisterMemberCommand.builder()
				.email("wodyd202@naver.com")
				.nickName("wodyd")
				.password("password").build();
		Member persistMember = new Member(command);
		memberRepository.save(persistMember);
		QueryMember member = new QueryMember(persistMember);
		memberQueryRepository.save(member);

		ApplicationListener<?> applicationListeners = new MemberAuthSuccessEventHandler(memberRepository);
		memberAuthHandleService = new MemberAuthHandleService(new MockApplicationEventPublisher(applicationListeners), memberQueryRepository, authMailSender);
	}
	
	@Test
	@DisplayName("이메일 인증번호 발급 후 인증 성공 테스트")
	void test_1() {
		AuthKey authKey = authMailSender.sendAuthMail(AuthKey.createAuthKey(new Email("wodyd202@naver.com")) ,false);
		memberAuthHandleService.matchAuthKey(authKey);
		Optional<QueryMember> findByEmail = memberQueryRepository.findByEmail("wodyd202@naver.com");
		assertThat(findByEmail.isPresent()).isTrue();
		QueryMember queryMember = findByEmail.get();
		assertThat(queryMember.getAuthType()).isEqualTo(AuthType.YES);
	}
}
