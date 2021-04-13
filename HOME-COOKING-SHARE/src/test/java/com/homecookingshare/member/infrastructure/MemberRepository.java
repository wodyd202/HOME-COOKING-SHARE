package com.homecookingshare.member.infrastructure;

import java.util.Optional;

import com.homecookingshare.member.domain.Email;
import com.homecookingshare.member.domain.aggregate.Member;

public interface MemberRepository {

	Optional<Member> findByEmail(Email email);

	void save(Member entity);

}
