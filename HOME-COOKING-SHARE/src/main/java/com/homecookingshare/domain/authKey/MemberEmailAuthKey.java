package com.homecookingshare.domain.authKey;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.domain.AbstractAggregateRoot;

import com.homecookingshare.command.memberEmailAuth.exception.InvalidEmailAuthenticationException;
import com.homecookingshare.command.memberEmailAuth.model.MemberEmailAuthCommand;
import com.homecookingshare.domain.authKey.event.AuthSuccessed;
import com.homecookingshare.domain.authKey.event.RegisterdEmailAuthKey;
import com.homecookingshare.domain.member.Email;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "MEMBER_EMAIL_AUTH_KEY")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberEmailAuthKey extends AbstractAggregateRoot<MemberEmailAuthKey>{
	
	@Id
	@GeneratedValue
	private Long seq;

	@Column(name = "auth_key")
	private String key;

	@Embedded
	private TargetEmail email;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createDateTime;

	public static MemberEmailAuthKey create(Email email) {
		MemberEmailAuthKey authKey = new MemberEmailAuthKey();
		authKey.email = new TargetEmail(email);
		authKey.key = UUID.randomUUID().toString().substring(0, 7);
		authKey.createDateTime = new Date();
		return authKey;
	}

	public void verifycation(MemberEmailAuthCommand.Verifycation command) {
		if(verifyOneDayPressed()) {
			registerEvent(new RegisterdEmailAuthKey(this.email));
			throw new InvalidEmailAuthenticationException("인증키가 만료되었습니다. 인증키를 재발급합니다.");
		}
		
		if(this.key.equals(command.getKey())) {
			registerEvent(new AuthSuccessed(this.email));
		}
	}

	private boolean verifyOneDayPressed() {
		return createDateTime.getTime() + 864000 < new Date().getTime();
	}
}
