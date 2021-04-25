package com.homecookingshare.query.eventHandler;

import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

import com.homecookingshare.command.member.entity.AuthKey;
import com.homecookingshare.command.member.entity.Member;
import com.homecookingshare.command.member.events.RegisterMemberEvent;
import com.homecookingshare.command.member.exception.InvalidMemberException;
import com.homecookingshare.command.member.values.AuthType;
import com.homecookingshare.command.member.values.Email;
import com.homecookingshare.common.mail.EmailUtilImpl;
import com.homecookingshare.query.infrastructure.MemberQueryRepository;
import com.homecookingshare.query.projections.QueryMember;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class AuthMailSender implements ApplicationListener<RegisterMemberEvent>{
	private final EmailUtilImpl emailUtilImpl;
	private final MemberQueryRepository memberQueryRepository;
	
	@Async
	@Override
	@Order(2)
	public void onApplicationEvent(RegisterMemberEvent event) {
		Member member = event.getMember();
		AuthKey createAuthKey = AuthKey.createAuthKey(new Email(member.getEmail().toString()));
		sendAuthMail(createAuthKey, false);
	}
	
	public AuthKey sendAuthMail(AuthKey createAuthKey,boolean checkExistEmail) {
		String email = createAuthKey.getEmail();
		verfyNotEmpltyEmail(email);
		
		checkExistEmail(checkExistEmail, email);
		
		log.info("before send mail to {}", email);
		memberQueryRepository.save(createAuthKey);
		emailUtilImpl.sendEmail(email, "[홈쿠킹 쉐어] 인증메일 입니다.", createAuthKey.getAuthKey());
		log.info("send mail to {}", email);
		return createAuthKey;
	}

	private void checkExistEmail(boolean checkExistEmail, String email) {
		if(checkExistEmail) {
			QueryMember member = memberQueryRepository.findByEmail(email)
			.orElseThrow(()->new InvalidMemberException("가입된 사용자 이메일이 존재하지 않습니다. 이메일을 다시확인해주세요.", "email"));
			if(member.getAuthType() == AuthType.YES) {
				throw new InvalidMemberException("이미 인증된 사용자입니다.", "email");
			}
		}
	}

	private void verfyNotEmpltyEmail(String email) {
		if(email == null || email.isEmpty()) {
			throw new InvalidMemberException("인증번호를 발급 받을 사용자 이메일을 입력해주세요.", "email");
		}
	}
	
}
