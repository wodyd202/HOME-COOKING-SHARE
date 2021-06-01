package com.homecookingshare.command.memberEmailAuth.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MemberEmailAuthCommand {
	
	@Getter
	@AllArgsConstructor
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class Verifycation {
		private String requireVerificationTargetEmail;
		private String key;
	}
}
