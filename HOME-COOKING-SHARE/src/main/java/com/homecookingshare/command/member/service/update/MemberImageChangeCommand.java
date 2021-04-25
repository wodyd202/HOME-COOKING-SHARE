package com.homecookingshare.command.member.service.update;

import org.springframework.web.multipart.MultipartFile;

import com.homecookingshare.command.member.values.Email;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MemberImageChangeCommand {
	private final Email email;
	private final MultipartFile file;
}
