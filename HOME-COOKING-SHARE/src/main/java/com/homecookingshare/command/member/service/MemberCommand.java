package com.homecookingshare.command.member.service;

import org.springframework.web.multipart.MultipartFile;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MemberCommand {
	
	@Getter
	@AllArgsConstructor
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class RegisterMemberCommand {
		private String email;
		private String password;
		private String nickName;
	}
	
	@Getter
	@AllArgsConstructor
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class EmailAuthenticationCommand {
		private String authNumber;
	}
	
	@Getter
	@AllArgsConstructor
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class ChangeImageCommand {
		private MultipartFile file;
	}
	
	@Getter
	@AllArgsConstructor
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class ChangePasswordCommand {
		private String originPassword;
		private String changePassword;
	}
}
