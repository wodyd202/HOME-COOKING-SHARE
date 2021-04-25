package com.homecookingshare.query.infrastructure;

import java.time.Duration;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.homecookingshare.command.member.entity.AuthKey;
import com.homecookingshare.query.projections.QueryMember;

@Repository
public class SimpleMemberRedisRepository implements MemberQueryRepository {

	@Autowired
	private RedisTemplate<String, Object> template;

	@Value("${redis.member}")
	private String MEMBER_KEY;

	@Value("${redis.member.auth}")
	private String MEMBER_AUTH_KEY;

	@Override
	public void save(QueryMember member) {
		String email = member.getEmail().toString();
		template.opsForValue().set(MEMBER_KEY + email, member);
	}

	@Override
	public void save(AuthKey key) {
		template.opsForValue().set(MEMBER_AUTH_KEY + key.getEmail(), key.getAuthKey(), Duration.ofDays(1));
	}

	@Override
	public Optional<QueryMember> findByEmail(String email) {
		Object findObj = template.opsForValue().get(MEMBER_KEY + email.toString());
		if (findObj == null) {
			return Optional.ofNullable(null);
		}
		return Optional.of((QueryMember) findObj);
	}

	@Override
	public Optional<AuthKey> findAuthKey(String email) {
		Object findObj = template.opsForValue().get(MEMBER_AUTH_KEY + email.toString());
		if (findObj == null) {
			return Optional.ofNullable(null);
		}
		return Optional.of(new AuthKey(email.toString(), findObj.toString()));
	}

}
