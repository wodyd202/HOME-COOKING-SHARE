package com.homecookingshare.member;

import java.util.Optional;

import com.homecookingshare.common.AbstractRegisterService;
import com.homecookingshare.common.Validator;
import com.homecookingshare.member.Email;
import com.homecookingshare.member.aggregate.Member;
import com.homecookingshare.member.aggregate.event.MemberEvent;
import com.homecookingshare.member.aggregate.event.MemberEvent.MemberEventType;
import com.homecookingshare.member.aggregate.exception.AlreadyExistMemberException;
import com.homecookingshare.member.infrastructure.MemberQueryRepository;
import com.homecookingshare.member.service.eventListener.MemberCommandListener;
import com.homecookingshare.member.service.eventListener.MemberQueryListener;
import com.homecookingshare.member.service.register.RegisterMember;

public class MemberRegisterService extends AbstractRegisterService<RegisterMember> {

	private final MemberQueryListener memberQueryListener;
	private final MemberCommandListener memberCommandListener;
	private final MemberQueryRepository memberQueryRepository;
	
	@Override
	protected void afterValidation(RegisterMember obj) {
		Optional<Member> findByEmail = memberQueryRepository.findByEmail(new Email(obj.getEmail()));
		if(findByEmail.isPresent()) {
			throw new AlreadyExistMemberException("가입된 이메일이 이미 존재합니다. 이메일을 다시 확인해주세요.", "email");
		}
	}
	
	@Override
	protected void save(RegisterMember obj) {
		MemberEvent event = new MemberEvent(new Email(obj.getEmail()), new Member(obj), MemberEventType.CREATE);
		memberCommandListener.onApplicationEvent(event);
		memberQueryListener.onApplicationEvent(event);
	}

	public MemberRegisterService(Validator<RegisterMember> memberRegisterValidator,MemberQueryRepository memberQueryRepository,
			MemberQueryListener memberQueryListener, MemberCommandListener memberCommandListener) {
		super(memberRegisterValidator);
		this.memberCommandListener = memberCommandListener;
		this.memberQueryListener = memberQueryListener;
		this.memberQueryRepository = memberQueryRepository;
	}
}
