package com.homecookingshare.member.service.register;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterMember {
	private String email;
	private String nickName;
	private String password;
}
