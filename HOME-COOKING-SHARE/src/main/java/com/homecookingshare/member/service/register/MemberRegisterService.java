package com.homecookingshare.member.service.register;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.homecookingshare.common.AbstractRegisterService;
import com.homecookingshare.common.Validator;
import com.homecookingshare.member.Email;
import com.homecookingshare.member.aggregate.Member;
import com.homecookingshare.member.aggregate.event.MemberEvent;
import com.homecookingshare.member.aggregate.event.MemberEvent.MemberEventType;
import com.homecookingshare.member.aggregate.exception.AlreadyExistMemberException;
import com.homecookingshare.member.infrastructure.MemberQueryRepository;

public class MemberRegisterService extends AbstractRegisterService<RegisterMember> {

	@Autowired
	private ApplicationEventPublisher publisher;
	private final MemberQueryRepository memberRedisRepository;
	private final PasswordEncoder passwordEncoder;
	
	@Override
	protected void afterValidation(RegisterMember obj) {
		Optional<Member> findByEmail = memberRedisRepository.findByEmail(new Email(obj.getEmail()));
		if (findByEmail.isPresent()) {
			throw new AlreadyExistMemberException("가입된 이메일이 이미 존재합니다. 이메일을 다시 확인해주세요.", "email");
		}
	}

	@Override
	protected void save(RegisterMember obj) {
		obj.encodePassword(passwordEncoder);
		MemberEvent event = new MemberEvent(new Email(obj.getEmail()), new Member(obj), MemberEventType.CREATE);
		publisher.publishEvent(event);
	}

	public MemberRegisterService(Validator<RegisterMember> memberRegisterValidator,
			MemberQueryRepository memberRedisRepository,PasswordEncoder passwordEncoder) {
		super(memberRegisterValidator);
		this.memberRedisRepository = memberRedisRepository;
		this.passwordEncoder = passwordEncoder;
	}
}
