package com.homecookingshare.member.service.load;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.homecookingshare.config.security.oauth.MemberPrincipal;
import com.homecookingshare.member.Email;
import com.homecookingshare.member.aggregate.Member;
import com.homecookingshare.member.infrastructure.SimpleMemberRedisRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberLoadService implements UserDetailsService {

	private final SimpleMemberRedisRepository memberRedisRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Member> member = memberRedisRepository.findByEmail(new Email(username));
		if (!member.isPresent()) {
			throw new IllegalArgumentException("이메일 혹은 패스워드가 일치하지 않습니다.");
		}
		return new MemberPrincipal(member.get());
	}

}
