package com.homecookingshare.member;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.homecookingshare.command.member.exception.InvalidImageFileException;
import com.homecookingshare.command.member.exception.InvalidMemberException;
import com.homecookingshare.command.member.infra.validator.ImageFileValidator;
import com.homecookingshare.command.member.infra.validator.RegisterMemberCommandValidator;
import com.homecookingshare.command.member.service.MemberCommand.ChangeImageCommand;
import com.homecookingshare.command.member.service.MemberCommand.RegisterMemberCommand;
import com.homecookingshare.common.Validator;

public class MemberValidatorTest {

	Validator<RegisterMemberCommand> memberValidator = new RegisterMemberCommandValidator();
	Validator<ChangeImageCommand> fileValidator = new ImageFileValidator();
	
	@Test
	void 사용자_이미지_변경시_이미지_파일이_아닐때_실패() {
		MultipartFile file = new MockMultipartFile("notImage.exe", new byte[] {});
		ChangeImageCommand command = new ChangeImageCommand(file);
		assertThrows(InvalidImageFileException.class, ()->{
			fileValidator.validate(command);
		});
	}
	
	@Test
	void 사용자_이메일형식이_올바르지_않을때_실패() {
		RegisterMemberCommand command = new RegisterMemberCommand("naver.com", "passwordPass", "nickName");
		assertThrows(InvalidMemberException.class, () -> {
			memberValidator.validate(command);
		});
	}
	
	@Test
	void 사용자_닉네임_누락() {
		RegisterMemberCommand command = new RegisterMemberCommand("test@naver.com", "passwordPass", "");
		assertThrows(InvalidMemberException.class, () -> {
			memberValidator.validate(command);
		});
	}

	@Test
	void 사용자_비밀번호_누락() {
		RegisterMemberCommand command = new RegisterMemberCommand("test@naver.com", "", "nickName");
		assertThrows(InvalidMemberException.class, () -> {
			memberValidator.validate(command);
		});
	}

	@Test
	void 사용자_이메일_누락() {
		RegisterMemberCommand command = new RegisterMemberCommand("", "passwordPass", "nickName");
		assertThrows(InvalidMemberException.class, () -> {
			memberValidator.validate(command);
		});
	}

	@Test
	void 정상_입력_케이스() {
		RegisterMemberCommand command = new RegisterMemberCommand("test@naver.com", "passwordPass", "nickName");
		memberValidator.validate(command);
	}
}
