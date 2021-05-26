package com.homecookingshare.domain.member;

import java.io.Serializable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "value")
public class Email implements Serializable{
	private static final long serialVersionUID = 1L;
	private String value;
	
	@Override
	public String toString() {
		return this.value;
	}
}
