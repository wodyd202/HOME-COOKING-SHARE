package com.homecookingshare.member.infrastructure;

import com.homecookingshare.member.aggregate.Member;

public interface MemberRedisRepository {
	public void save(Member member);
}
