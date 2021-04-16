package com.homecookingshare.member.aggregate;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.domain.AbstractAggregateRoot;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.homecookingshare.member.Email;
import com.homecookingshare.member.Password;
import com.homecookingshare.member.Profile;
import com.homecookingshare.member.aggregate.exception.InvalidMemberException;
import com.homecookingshare.member.service.register.RegisterMember;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 
 * @author LJY
 * 
 *    1. 반드시 이메일 형식을 지켜야한다.
 *    2. 이메일은 100자 이하로 한정한다.
 *    2. nickname은 [영문,한글] 조합으로 8자 이하까지 허용한다 
 *    3. 비밀번호는 [영어(소,대문자),숫자] 조합으로 8자 이상 16자 이하로 한다 
 *    4. 로그인은 반드시 이메일 인증된 사용자만 허용한다
 * 
 */

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends AbstractAggregateRoot<Member> implements Serializable{
	private static final long serialVersionUID = 1L;

	private Email email;
	private Password password;
	private Profile profile;
	private MemberRole role;
	private AuthType authType;
	private MemberState state;
	private Date createDateTime;

	public void authSuccess() {
		if(this.authType == AuthType.YES) {
			throw new InvalidMemberException("이미 인증된 사용자입니다.", "email");
		}
		this.authType = AuthType.YES;
	}
	
	@JsonIgnore
	public boolean isDelete() {
		return this.state == MemberState.DELETE;
	}
	
	public void changeProfileImage(String fileName) {
		this.profile = new Profile(this.profile.getNickName(), fileName);
	}

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
