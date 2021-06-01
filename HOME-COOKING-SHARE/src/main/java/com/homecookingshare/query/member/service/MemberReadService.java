package com.homecookingshare.query.member.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.homecookingshare.command.member.exception.MemberNotFoundException;
import com.homecookingshare.config.security.MemberPrincipal;
import com.homecookingshare.domain.member.Email;
import com.homecookingshare.domain.member.read.Member;
import com.homecookingshare.query.member.infra.MemberReadRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MemberReadService implements UserDetailsService{
	private MemberReadRepository memberReadRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member findMember = memberReadRepository.findByEmail(new Email(username))
				.orElseThrow(()->new MemberNotFoundException("해당 이메일의 회원이 존재하지 않습니다."));
		return new MemberPrincipal(findMember);
	}

}
