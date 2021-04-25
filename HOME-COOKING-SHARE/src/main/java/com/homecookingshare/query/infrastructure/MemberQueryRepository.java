package com.homecookingshare.query.infrastructure;

import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.homecookingshare.command.member.entity.AuthKey;
import com.homecookingshare.query.projections.QueryMember;

public interface MemberQueryRepository {
	Optional<QueryMember> findByEmail(String email);

	Optional<AuthKey> findAuthKey(String email);

	@Transactional
	void save(QueryMember member);

	@Transactional
	void save(AuthKey key);
}
