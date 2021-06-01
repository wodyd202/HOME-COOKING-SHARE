package com.homecookingshare.domain.member;

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
import org.springframework.security.crypto.password.PasswordEncoder;

import com.homecookingshare.command.member.model.MemberCommand.RegisterMemberCommand;
import com.homecookingshare.domain.member.event.ChangedMemberImage;
import com.homecookingshare.domain.member.event.ChangedMemberPassword;
import com.homecookingshare.domain.member.event.RegisterdMember;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "MEMBER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
public class Member extends AbstractAggregateRoot<Member> implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	@AttributeOverride(name = "value", column = @Column(name = "email", length = 100))
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
	private MemberRule role;

	@Enumerated(EnumType.STRING)
	private AuthType authType;

	@Enumerated(EnumType.STRING)
	private MemberState state;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createDateTime;

	public Member(Email email) {
		this.email = email;
	}

	protected Member(String email, String password, String nickName) {
		this.email = new Email(email);
		this.password = new Password(password);
		this.profile = new Profile(nickName);
		this.role = MemberRule.MEMBER;
		this.authType = AuthType.NO;
		this.state = MemberState.CREATE;
		this.createDateTime = new Date();
		registerEvent(new RegisterdMember(this.email, this.password, this.profile));
	}

	public static Member create(PasswordEncoder passwordEncoder, RegisterMemberCommand command) {
		Member member = new Member(command.getEmail(), command.getPassword(), command.getNickName());
		member.password.encode(passwordEncoder);
		return member;
	}
	
	public boolean isDeleted() {
		return this.state == MemberState.DELETE;
	}
	
	public boolean isNotAuth() {
		return authType == AuthType.NO;
	}

	public void changeImage(String saveFileName) {
		this.profile.changeImage(saveFileName);
		registerEvent(new ChangedMemberImage(this.email, saveFileName));
	}

	public void changePassword(PasswordEncoder encoder, String changePassword) {
		this.password = new Password(encoder.encode(changePassword));
		registerEvent(new ChangedMemberPassword(this.email, this.password));
	}

}
