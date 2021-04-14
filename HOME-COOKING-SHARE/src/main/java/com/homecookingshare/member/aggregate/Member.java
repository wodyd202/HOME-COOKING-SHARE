package com.homecookingshare.member.aggregate;

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

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.AbstractAggregateRoot;

import com.homecookingshare.member.Email;
import com.homecookingshare.member.Password;
import com.homecookingshare.member.Profile;
import com.homecookingshare.member.aggregate.event.MemberEvent;
import com.homecookingshare.member.aggregate.event.MemberEvent.MemberEventType;
import com.homecookingshare.member.service.register.RegisterMember;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 
 * @author LJY
 * 
 *         1. 반드시 이메일 형식을 지켜야한다. 
 *         2. nickname은 영문 혹은 한글만 허용한다
 *         3. 비밀번호는 영어(소,대문자) 혹은 숫자 조합으로 8자 이상 16자 이하로 한다 
 *         4. 로그인은 반드시 이메일 인증된 사용자만 허용한다
 * 
 */

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends AbstractAggregateRoot<Member>{

	@EmbeddedId
	@AttributeOverride(name = "value", column = @Column(length = 100, name = "email"))
	private Email email;

	@Embedded
	@AttributeOverride(name = "value", column = @Column(length = 200, name = "password"))
	private Password password;

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "nick_name", column = @Column(length = 30, nullable = false)),
		@AttributeOverride(name = "img_path", column = @Column(length = 100))
	})
	private Profile profile;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 6)
	private MemberRole role;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 3)
	private AuthType authType;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 6)
	private MemberState state;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createDateTime;

	public void changeProfileImage(String fileName) {
		this.profile = new Profile(this.profile.getNickName(), fileName);
	}
	
	public Member(RegisterMember registerMember, ApplicationEventPublisher publisher) {
		this.email = new Email(registerMember.getEmail());
		this.password = new Password(registerMember.getPassword());
		this.profile = new Profile(registerMember);

		this.role = MemberRole.MEMBER;
		this.authType = AuthType.NO;
		this.state = MemberState.CREATE;

		this.createDateTime = new Date();
		
		if(publisher != null) {
			MemberEvent registerEvent = registerEvent(new MemberEvent(this,MemberEventType.CREATE));
			publisher.publishEvent(registerEvent);
		}
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
