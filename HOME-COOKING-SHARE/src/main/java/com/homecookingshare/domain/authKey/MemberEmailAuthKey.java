package com.homecookingshare.domain.authKey;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "MEMBER_EMAIL_AUTH_KEY")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberEmailAuthKey {
	
	@Id
	@GeneratedValue
	private Long seq;
	
	private String key;

	@Embedded
	private TargetEmail email;
}
