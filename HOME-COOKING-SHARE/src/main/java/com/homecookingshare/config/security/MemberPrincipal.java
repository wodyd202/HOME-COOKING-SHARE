package com.homecookingshare.config.security;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.homecookingshare.domain.member.AuthType;
import com.homecookingshare.domain.member.read.Member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberPrincipal implements UserDetails {
	private static final long serialVersionUID = 1L;
	private Member member;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_" + member.getRule()));
	}

	@Override
	public String getPassword() {
		return member.getPassword().getValue();
	}

	@Override
	public String getUsername() {
		return member.getEmail().getValue();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
//		return member.getAuthType() == AuthType.YES;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
