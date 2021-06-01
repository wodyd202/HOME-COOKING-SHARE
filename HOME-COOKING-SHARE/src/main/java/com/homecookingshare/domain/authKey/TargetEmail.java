package com.homecookingshare.domain.authKey;

import java.io.Serializable;

import com.homecookingshare.domain.member.Email;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TargetEmail implements Serializable {
	private static final long serialVersionUID = 1L;
	private String email;

	public TargetEmail(Email email) {
		this.email = email.getValue();
	}
}
