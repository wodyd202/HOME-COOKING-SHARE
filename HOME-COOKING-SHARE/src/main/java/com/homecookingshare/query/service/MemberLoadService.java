package com.homecookingshare.query.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.homecookingshare.config.security.oauth.MemberPrincipal;
import com.homecookingshare.query.infrastructure.SimpleMemberRedisRepository;
import com.homecookingshare.query.projections.QueryMember;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberLoadService implements UserDetailsService {
	private final SimpleMemberRedisRepository memberRedisRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<QueryMember> member = memberRedisRepository.findByEmail(username);
		if(!member.isPresent()) {
			throw new UsernameNotFoundException("유저의 이메일 혹은 비밀번호가 일치하지 않습니다.");
		}
		return new MemberPrincipal(member.get());
	}

}
