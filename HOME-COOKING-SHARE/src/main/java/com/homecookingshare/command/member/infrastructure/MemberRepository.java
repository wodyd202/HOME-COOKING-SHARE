package com.homecookingshare.command.member.infrastructure;

import java.util.Optional;

import com.homecookingshare.command.member.entity.Member;
import com.homecookingshare.command.member.values.Email;

public interface MemberRepository {
	Member save(Member entity);

	Optional<Member> findByEmail(Email email);
}
