package com.homecookingshare.command.member.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.domain.AbstractAggregateRoot;

import com.homecookingshare.command.member.exception.InvalidMemberException;
import com.homecookingshare.command.member.service.register.RegisterMemberCommand;
import com.homecookingshare.command.member.values.AuthType;
import com.homecookingshare.command.member.values.Email;
import com.homecookingshare.command.member.values.MemberRole;
import com.homecookingshare.command.member.values.MemberState;
import com.homecookingshare.command.member.values.Password;
import com.homecookingshare.command.member.values.Profile;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 
 * @author LJY
 * 
 *         1. 반드시 이메일 형식을 지켜야한다. 
 *         2. 이메일은 100자 이하로 한정한다. 
 *         3. nickname은 [영문,한글] 조합으로 8자 이하까지 허용한다. 
 *         4. 비밀번호는 [영어(소,대문자),숫자] 조합으로 8자 이상 16자 이하로 한다. 
 *         5. 로그인은 반드시 이메일 인증된 사용자만 허용한다.
 * 
 */

@Entity
@Getter
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
public class Member extends AbstractAggregateRoot<Member> implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	@AttributeOverride(name = "value", column = @Column(name = "email", nullable = false, length = 100))
	private Email email;
	
	@Embedded
	@AttributeOverride(name = "value", column = @Column(name = "password", nullable = false, length = 100))
	private Password password;

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "nickName", column = @Column(name = "nickName", nullable = false, length = 100)),
		@AttributeOverride(name = "img", column = @Column(name = "img", length = 100))
	})
	private Profile profile;
	
	@Enumerated(EnumType.STRING)
	private MemberRole role;

	@Enumerated(EnumType.STRING)
	private AuthType authType;
	
	@Enumerated(EnumType.STRING)
	private MemberState state;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDateTime;

	public void authSuccess() {
		if (this.authType == AuthType.YES) {
			throw new InvalidMemberException("이미 인증된 사용자입니다.", "email");
		}
		this.authType = AuthType.YES;
	}

	public boolean isAuthSuccess() {
		return this.authType == AuthType.YES;
	}

	public boolean isDelete() {
		return this.state == MemberState.DELETE;
	}

	public void changeProfileImage(String fileName) {
		this.profile = new Profile(this.profile.getNickName(), fileName);
	}

	public void initCreateDate() {
		this.createDateTime = new Date();
	}
	
	public Member(RegisterMemberCommand registerMember) {
		this.email = new Email(registerMember.getEmail());
		this.password = new Password(registerMember.getPassword());
		this.profile = new Profile(registerMember);

		this.role = MemberRole.MEMBER;
		this.authType = AuthType.NO;
		this.state = MemberState.CREATE;

		initCreateDate();
	}

}
