package com.homecookingshare.memberEmailAuth;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.homecookingshare.command.member.infra.JpaMemberRepository;
import com.homecookingshare.command.memberEmailAuth.exception.InvalidEmailAuthenticationException;
import com.homecookingshare.command.memberEmailAuth.infra.JpaMemberEmailAuthRepository;
import com.homecookingshare.command.memberEmailAuth.model.MemberEmailAuthCommand;
import com.homecookingshare.command.memberEmailAuth.model.MemberEmailAuthCommand.Verifycation;
import com.homecookingshare.command.memberEmailAuth.service.MemberEmailAuthService;
import com.homecookingshare.command.memberEmailAuth.service.SimpleMemberEmailAuthService;
import com.homecookingshare.common.Validator;
import com.homecookingshare.common.mail.MailUtil;
import com.homecookingshare.domain.authKey.MemberEmailAuthKey;
import com.homecookingshare.domain.authKey.TargetEmail;
import com.homecookingshare.domain.member.Email;
import com.homecookingshare.domain.member.Member;
import com.homecookingshare.domain.member.event.RegisterdMember;

@SuppressWarnings("unchecked")
public class MemberEmailAuthServiceTest {
	JpaMemberRepository memberRepository = mock(JpaMemberRepository.class);
	JpaMemberEmailAuthRepository repository = mock(JpaMemberEmailAuthRepository.class);
	MemberEmailAuthService authService = new SimpleMemberEmailAuthService(memberRepository, repository, mock(MailUtil.class));

	@Test
	void 사용자_인증_실패() {
		when(memberRepository.existsById(any(Email.class)))
			.thenReturn(true);
		MemberEmailAuthKey mockAuthKey = mock(MemberEmailAuthKey.class);
		doThrow(InvalidEmailAuthenticationException.class)
		.when(mockAuthKey)
			.verifycation(any(Verifycation.class));
		
		when(repository.findTop1ByEmailOrderBySeqDesc(any(TargetEmail.class)))
		.thenReturn(Optional.of(mockAuthKey));
	
		MemberEmailAuthCommand.Verifycation command = new MemberEmailAuthCommand.Verifycation("test@naver.com","key");
		assertThrows(InvalidEmailAuthenticationException.class, ()->{
			authService.verifycation(mock(Validator.class), command);
		});
	}
	
	@Test
	void 사용자_정상_인증() {
		when(memberRepository.existsById(any(Email.class)))
			.thenReturn(true);
		
		MemberEmailAuthKey mockAuthKey = mock(MemberEmailAuthKey.class);
		assertDoesNotThrow(()->{
			mockAuthKey.verifycation(any(MemberEmailAuthCommand.Verifycation.class));
		});
		
		when(repository.findTop1ByEmailOrderBySeqDesc(any(TargetEmail.class)))
			.thenReturn(Optional.of(mockAuthKey));
		
		MemberEmailAuthCommand.Verifycation command = new MemberEmailAuthCommand.Verifycation("test@naver.com","key");
		authService.verifycation(mock(Validator.class), command);
	}
	
	@Test
	void 회원가입_후_인증키_생성() {
		MockMember mockMember = new MockMember("dsakl");
		mockMember.getDomainEvents().forEach(event -> {
			authService.create((RegisterdMember) event);
		});
		
		verify(repository,times(1))
			.save(any(MemberEmailAuthKey.class));
	}

	public class MockMember extends Member {
		private static final long serialVersionUID = 1L;

		Collection<Object> getDomainEvents() {
			return super.domainEvents();
		}

		public MockMember(String email) {
			super(email, null, null);
		}
	}
}
