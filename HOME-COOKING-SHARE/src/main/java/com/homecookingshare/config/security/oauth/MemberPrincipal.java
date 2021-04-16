package com.homecookingshare.config.security.oauth;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.homecookingshare.member.aggregate.Member;
import com.homecookingshare.member.aggregate.Member.MemberState;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberPrincipal implements UserDetails {
	private static final long serialVersionUID = 1L;
	private Member member;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		String role = member.getRole().toString();
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_" + role));
	}

	@Override
	public String getPassword() {
		return member.getPassword().toString();
	}

	@Override
	public String getUsername() {
		return member.getEmail().toString();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return member.getState() == MemberState.CREATE;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
//		return member.getAuthType() == AuthType.YES;
	}

}
