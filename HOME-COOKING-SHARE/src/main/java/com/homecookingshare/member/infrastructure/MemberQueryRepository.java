package com.homecookingshare.member.infrastructure;

import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.homecookingshare.member.AuthKey;
import com.homecookingshare.member.Email;
import com.homecookingshare.member.aggregate.Member;

public interface MemberQueryRepository {
	Optional<Member> findByEmail(Email email);
	
	Optional<AuthKey> findAuthKey(Email email);
	
	@Transactional
	void save(Member member);

	@Transactional
	void save(AuthKey key);
}
