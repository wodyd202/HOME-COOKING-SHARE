package com.homecookingshare.member.fake;

import java.util.HashMap;
import java.util.Optional;

import com.homecookingshare.command.member.entity.Member;
import com.homecookingshare.command.member.infrastructure.MemberRepository;
import com.homecookingshare.command.member.values.Email;

public class FakeMemberRepository implements MemberRepository {

	private final HashMap<Email, Member> repo = new HashMap<>();
	
	@Override
	public Member save(Member entity) {
		return repo.put(entity.getEmail(), entity);
	}

	@Override
	public Optional<Member> findByEmail(Email email) {
		return Optional.ofNullable(repo.get(email));
	}
	
}
