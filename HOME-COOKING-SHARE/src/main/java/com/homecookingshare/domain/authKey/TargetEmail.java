package com.homecookingshare.domain.authKey;

import java.io.Serializable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TargetEmail implements Serializable{
	private static final long serialVersionUID = 1L;
	private String email;
}
