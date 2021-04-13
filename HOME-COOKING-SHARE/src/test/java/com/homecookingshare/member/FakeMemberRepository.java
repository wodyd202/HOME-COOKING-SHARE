package com.homecookingshare.member;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.homecookingshare.member.domain.Email;
import com.homecookingshare.member.domain.aggregate.Member;
import com.homecookingshare.member.infrastructure.MemberRepository;

public class FakeMemberRepository implements MemberRepository {

	private final Map<String, Member> repository = new HashMap<>();
	
	@Override
	public Optional<Member> findByEmail(Email email) {
		Member member = repository.get(email.toString());
		return Optional.ofNullable(member);
	}

	@Override
	public void save(Member entity) {
		repository.put(entity.getEmail().toString(), entity);
	}

}
