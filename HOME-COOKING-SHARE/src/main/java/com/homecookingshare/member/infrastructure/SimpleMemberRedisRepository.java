package com.homecookingshare.member.infrastructure;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.homecookingshare.member.AuthKey;
import com.homecookingshare.member.Email;
import com.homecookingshare.member.aggregate.Member;

@Repository
public class SimpleMemberRedisRepository implements MemberQueryRepository{
	
	@Autowired
	private RedisTemplate<String, Object> template;

	@Value("${redis.member}")
	private String MEMBER_KEY;
	
	@Value("${redis.member.auth}")
	private String MEMBER_AUTH_KEY;
	
	@Override
	public void save(Member member) {
		String email = member.getEmail().toString();
		template.opsForValue().set(MEMBER_KEY + email, member);
	}


	@Override
	public void save(AuthKey key) {
	}
	
	@Override
	public Optional<Member> findByEmail(Email email) {
		Object findObj = template.opsForValue().get(MEMBER_KEY + email.toString());
		if(findObj == null) {
			return Optional.ofNullable(null);
		}
		return Optional.of((Member) findObj);
	}


	@Override
	public Optional<AuthKey> findAuthKey(Email email) {
		return null;
	}

}
