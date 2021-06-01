package com.homecookingshare.query.member.infra;

import java.text.ParseException;
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

import com.homecookingshare.domain.member.AuthType;
import com.homecookingshare.domain.member.Email;
import com.homecookingshare.domain.member.MemberRule;
import com.homecookingshare.domain.member.MemberState;
import com.homecookingshare.domain.member.Password;
import com.homecookingshare.domain.member.Profile;
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
		if(member.getProfile().getImg() != null) {
			hashOperations.put(MEMBER_KEY + email.getValue(), "image", member.getProfile().getImg());
		}
	}

	@Override
	public Optional<Member> findByEmail(Email email) {
		HashOperations hashOperations = redisTemplate.opsForHash();
		if(hashOperations.get(MEMBER_KEY + email.getValue(), "rule") == null) {
			return Optional.ofNullable(null);
		}

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

		try {
			Member member = Member.builder()
				.email(email)
				.password(new Password(hashOperations.get(MEMBER_KEY + email.getValue(), "password").toString()))
				.authType(AuthType.valueOf(hashOperations.get(MEMBER_KEY + email.getValue(), "authType").toString()))
				.rule(MemberRule.valueOf(hashOperations.get(MEMBER_KEY + email.getValue(), "rule").toString()))
				.state(MemberState.valueOf(hashOperations.get(MEMBER_KEY + email.getValue(), "state").toString()))
				.profile(new Profile(hashOperations.get(MEMBER_KEY + email.getValue(), "nickName").toString()))
				.createDateTime(simpleDateFormat.parse(hashOperations.get(MEMBER_KEY + email.getValue(), "createDateTime").toString()))
				.build();
			if(hashOperations.get(MEMBER_KEY + email.getValue(), "image") != null) {
				member.changeImage(hashOperations.get(MEMBER_KEY + email.getValue(), "image").toString());
			}
			return Optional.of(member);
		} catch (ParseException e) {
			throw new IllegalArgumentException();
		}
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
