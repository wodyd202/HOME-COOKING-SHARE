package com.homecookingshare.member.fake;

import java.util.HashMap;
import java.util.Optional;

import com.homecookingshare.command.member.entity.AuthKey;
import com.homecookingshare.query.infrastructure.MemberQueryRepository;
import com.homecookingshare.query.projections.QueryMember;

public class FakeMemberQueryRepository implements MemberQueryRepository {

	private final HashMap<String, QueryMember> repo = new HashMap<>();
	private final HashMap<String, AuthKey> authRepo = new HashMap<>();
	
	@Override
	public Optional<QueryMember> findByEmail(String email) {
		return Optional.ofNullable(repo.get(email));
	}

	@Override
	public Optional<AuthKey> findAuthKey(String email) {
		return Optional.ofNullable(authRepo.get(email));
	}

	@Override
	public void save(QueryMember member) {
		repo.put(member.getEmail(), member);
	}

	@Override
	public void save(AuthKey key) {
		authRepo.put(key.getEmail(), key);
	}

}
