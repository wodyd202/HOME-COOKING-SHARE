package com.homecookingshare.member.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.homecookingshare.member.aggregate.Member;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class MemberRedisRepository {
	
	@Autowired
	private RedisTemplate<String, Object> template;
	
	public void save(Member member) {
		String email = member.getEmail().toString();
		template.opsForValue().set(email, member);
		log.info("member save into redis {}", email);
	}
	
}
