package com.homecookingshare.query.member.infra;

import java.util.List;
import java.util.Optional;

import com.homecookingshare.domain.member.Email;
import com.homecookingshare.domain.member.read.Member;

public interface MemberReadRepository {
	void save(Member member);

	Optional<Member> findLoginInfoByEmail(Email member);
	
	List<Member> findAll();
}
