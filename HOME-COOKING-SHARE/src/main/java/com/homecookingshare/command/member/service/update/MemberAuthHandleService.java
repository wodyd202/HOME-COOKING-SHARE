package com.homecookingshare.command.member.service.update;

import org.springframework.context.ApplicationEventPublisher;

import com.homecookingshare.command.member.entity.AuthKey;
import com.homecookingshare.command.member.exception.InvalidAuthKeyException;
import com.homecookingshare.command.member.exception.MemberNotFoundException;
import com.homecookingshare.command.member.values.Email;
import com.homecookingshare.query.eventHandler.AuthMailSender;
import com.homecookingshare.query.infrastructure.MemberQueryRepository;
import com.homecookingshare.query.projections.QueryMember;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberAuthHandleService {
	private final ApplicationEventPublisher publisher;
	private final MemberQueryRepository memberQueryRepository;
	private final AuthMailSender authMailSender;
	
	public void matchAuthKey(AuthKey authKey) {
		verfyNotEmptyAuthKey(authKey);
		String email = authKey.getEmail();
		QueryMember member = memberQueryRepository.findByEmail(email)
				.orElseThrow(() -> new MemberNotFoundException("해당 이메일로 가입된 사용자가 존재하지 않습니다. 이메일을 다시 확인해주세요.", "email"));
		
		AuthKey findAuthKey = memberQueryRepository.findAuthKey(email).orElseThrow(() -> {
			authMailSender.sendAuthMail(AuthKey.createAuthKey(new Email(email)), true);
			new InvalidAuthKeyException("인증메일을 발급하지 않았거나, 혹은 인증메일 기간이 만료되었습니다. 인증번호를 재전송합니다.", "email");
			return null;
		});

		match(authKey, member, findAuthKey);
	}

	private void verfyNotEmptyAuthKey(AuthKey authKey) {
		String key = authKey.getAuthKey();
		String email = authKey.getEmail();
		if(key == null || key.isEmpty()) {
			throw new InvalidAuthKeyException("인증번호를 입력해주세요.", "authKey");
		}
		if(email == null || email.isEmpty()) {
			throw new InvalidAuthKeyException("인증할 사용자의 이메일을 입력해주세요.", "email");
		}
	}

	private void match(AuthKey authKey, QueryMember member, AuthKey findAuthKey) {
		if (findAuthKey.equals(authKey)) {
			member.authSuccess();
			memberQueryRepository.save(member);
			publisher.publishEvent(new MemberAuthSuccessEvent(new Email(member.getEmail())));
		} else {
			throw new InvalidAuthKeyException("인증번호가 일치하지 않습니다. 다시 확인해주세요.", "email");
		}
	}
}
