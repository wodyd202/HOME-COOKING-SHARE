package com.homecookingshare.member;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.homecookingshare.member.aggregate.Member;
import com.homecookingshare.member.infrastructure.MemberQueryRepository;

public class FakeMemberQueryRepository implements MemberQueryRepository {
	
	private final Map<String, Member> repository = new HashMap<>();
	
	@Override
	public Optional<Member> findByEmail(Email email) {
		return Optional.ofNullable(repository.get(email.toString()));
	}

	@Override
	public Optional<AuthKey> findAuthKey(Email email) {
		return null;
	}

	@Override
	public void save(Member member) {
		repository.put(member.getEmail().toString(), member);
	}

	@Override
	public void save(AuthKey key) {
	}

}
