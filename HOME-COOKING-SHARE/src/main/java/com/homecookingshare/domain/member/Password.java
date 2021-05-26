package com.homecookingshare.domain.member;

import java.io.Serializable;

import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "value")
public class Password implements Serializable {
	private static final long serialVersionUID = 1L;
	private String value;

	@Override
	public String toString() {
		return this.value;
	}

	public void encode(PasswordEncoder passwordEncoder) {
		this.value = passwordEncoder.encode(this.value);
	}
}
