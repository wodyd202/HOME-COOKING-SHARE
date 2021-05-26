package com.homecookingshare.command.member.infra;

import java.util.Optional;

import com.homecookingshare.domain.member.Email;
import com.homecookingshare.domain.member.Member;

public interface MemberRepository {
	Member save(Member entity);

	Optional<Member> findByEmail(Email email);
}
