package com.homecookingshare.query.member.infra;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.query.SortQuery;
import org.springframework.data.redis.core.query.SortQueryBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.homecookingshare.domain.member.AuthType;
import com.homecookingshare.domain.member.Email;
import com.homecookingshare.domain.member.MemberRule;
import com.homecookingshare.domain.member.MemberState;
import com.homecookingshare.domain.member.Password;
import com.homecookingshare.domain.member.read.Member;

@Repository
@SuppressWarnings({"rawtypes","unchecked"})
public class RedisMemberReadRepository implements MemberReadRepository{
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@Value("${redis.member.key}")
	private String MEMBER_KEY;
	
	@Value("${redis.member-list.key}")
	private String MEMBER_LIST_KEY;
	
	@Override
	@Transactional
	public void save(Member member) {
		Email email = member.getEmail();
		SetOperations opsForSet = redisTemplate.opsForSet();
		opsForSet.add(MEMBER_LIST_KEY, email.getValue());
		
		HashOperations hashOperations = redisTemplate.opsForHash();
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		hashOperations.put(MEMBER_KEY + email.getValue(), "authType", member.getAuthType().toString());
		hashOperations.put(MEMBER_KEY + email.getValue(), "createDateTime", simpleDateFormat.format(member.getCreateDateTime()));
		hashOperations.put(MEMBER_KEY + email.getValue(), "password", member.getPassword().getValue());
		hashOperations.put(MEMBER_KEY + email.getValue(), "nickName", member.getProfile().getNickName());
		hashOperations.put(MEMBER_KEY + email.getValue(), "rule", member.getRule().toString());
		hashOperations.put(MEMBER_KEY + email.getValue(), "state", member.getState().toString());
	}

	@Override
	public Optional<Member> findLoginInfoByEmail(Email email) {
		HashOperations hashOperations = redisTemplate.opsForHash();
		if(hashOperations.hasKey(MEMBER_KEY + email.getValue(), "rule") == null) {
			return Optional.ofNullable(null);
		}
		
		return Optional.of(Member.builder()
			.email(email)
			.password(new Password(hashOperations.get(MEMBER_KEY + email.getValue(), "password").toString()))
			.authType(AuthType.valueOf(hashOperations.get(MEMBER_KEY + email.getValue(), "authType").toString()))
			.rule(MemberRule.valueOf(hashOperations.get(MEMBER_KEY + email.getValue(), "rule").toString()))
			.state(MemberState.valueOf(hashOperations.get(MEMBER_KEY + email.getValue(), "state").toString()))
			.build());
	}

	@Override
	public List<Member> findAll() {
		SortQuery<String> query = SortQueryBuilder.sort(MEMBER_LIST_KEY)
				.get(MEMBER_KEY + "*->state")
				.get(MEMBER_KEY + "*->nickName")
				.alphabetical(true)
				.build();
		redisTemplate.sort(query).forEach(c->{
			
		});
		return null;
	}

}
