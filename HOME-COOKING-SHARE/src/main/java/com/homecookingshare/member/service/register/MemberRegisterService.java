package com.homecookingshare.member.service.register;

import com.homecookingshare.common.AbstractRegisterService;
import com.homecookingshare.common.Validator;
import com.homecookingshare.member.aggregate.Member;
import com.homecookingshare.member.infrastructure.MemberRepository;

public class MemberRegisterService extends AbstractRegisterService<RegisterMember> {

	private final MemberRepository memberRepository;
	
	@Override
	protected void save(RegisterMember obj) {
		Member entity = new Member(obj, eventPublisher);
		memberRepository.save(entity);
	}

	public MemberRegisterService(Validator<RegisterMember> validate, MemberRepository memberRepository) {
		super(validate);
		this.memberRepository = memberRepository;
	}

}
