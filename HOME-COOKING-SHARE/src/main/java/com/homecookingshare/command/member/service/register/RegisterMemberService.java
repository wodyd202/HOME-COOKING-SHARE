package com.homecookingshare.command.member.service.register;

import java.util.Optional;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.homecookingshare.command.member.entity.Member;
import com.homecookingshare.command.member.events.RegisterMemberEvent;
import com.homecookingshare.command.member.exception.AlreadyExistMemberException;
import com.homecookingshare.command.member.infrastructure.MemberRepository;
import com.homecookingshare.command.member.values.Email;
import com.homecookingshare.common.AbstractRegisterService;
import com.homecookingshare.common.Validator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RegisterMemberService extends AbstractRegisterService<RegisterMemberCommand> {
	private final MemberRepository memberRepository;
	private final PasswordEncoder encoder;
	private final int ONE_DAY = 24 * 60 * 60 * 1000 * 1;
	
	@Override
	protected void afterValidation(RegisterMemberCommand obj) {
		Optional<Member> findByEmail = memberRepository.findByEmail(new Email(obj.getEmail()));
		if (findByEmail.isPresent()) {
			authProcess(findByEmail);
			throw new AlreadyExistMemberException("가입된 이메일이 이미 존재합니다. 이메일을 다시 확인해주세요.", "email");
		}
		obj.encodePassword(encoder);
	}

	@Transactional(noRollbackFor = AlreadyExistMemberException.class)
	private void authProcess(Optional<Member> findByEmail) {
		Member member = findByEmail.get();
		boolean dayPassed = member.getCreateDateTime().getTime() + ONE_DAY < member.getCreateDateTime().getTime();
		if(!member.isAuthSuccess()) {
			if(dayPassed) {
				member.initCreateDate();
			}
			throw new AlreadyExistMemberException("이메일 인증이 되지 않은 사용자입니다. 이메일 인증 후 로그인하십시오.", "email");
		}
	}

	@Override
	protected void save(RegisterMemberCommand obj) {
		Member member = new Member(obj);
		eventPublisher.publishEvent(new RegisterMemberEvent(member));
		memberRepository.save(member);
		log.info("member create {}", member);
	}

	public RegisterMemberService(ApplicationEventPublisher publisher,Validator<RegisterMemberCommand> validate, MemberRepository memberRepository,
			PasswordEncoder encoder) {
		super(validate);
		super.eventPublisher = publisher;
		this.memberRepository = memberRepository;
		this.encoder = encoder;
	}

}
