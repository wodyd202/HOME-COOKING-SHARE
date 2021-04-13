package com.homecookingshare.member.domain.aggregate;

import java.util.Date;

import com.homecookingshare.member.domain.Email;
import com.homecookingshare.member.domain.Password;
import com.homecookingshare.member.domain.Profile;
import com.homecookingshare.member.service.register.RegisterMember;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 
 * @author LJY
 * 
 * 1. 반드시 이메일 형식을 지켜야한다.
 * 2. nickname은 영문 혹은 한글만 허용한다
 * 3. 비밀번호는 영어(소,대문자) 혹은 숫자 조합으로 8자 이상 16자 이하로 한다
 * 4. 로그인은 반드시 이메일 인증된 사용자만 허용한다
 * 
 */

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "email")
public class Member {
	private Email email;
	private Password password;
	private Profile profile;

	private MemberRole role;
	private AuthType authType;
	private MemberState state;
	
	private Date createDateTime;

	public Member(RegisterMember registerMember) {
		this.email = new Email(registerMember.getEmail());
		this.password = new Password(registerMember.getPassword());
		this.profile = new Profile(registerMember);
		
		this.role = MemberRole.MEMBER;
		this.authType = AuthType.NO;
		this.state = MemberState.CREATE;
		
		this.createDateTime = new Date();
	}
	
	public enum AuthType {
		YES, NO
	}

	public enum MemberRole {
		MEMBER, ADMIN
	}

	public enum MemberState {
		CREATE, DELETE
	}
}
