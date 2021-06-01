package com.homecookingshare.command.member.infra.validator;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.homecookingshare.command.member.exception.InvalidImageFileException;
import com.homecookingshare.command.member.model.MemberCommand.ChangeImageCommand;
import com.homecookingshare.common.Validator;

@Component
public class ImageFileValidator implements Validator<ChangeImageCommand> {

	@Override
	public void validate(ChangeImageCommand target) {
		MultipartFile file = target.getFile();
		verifyNotNullObject(file, new InvalidImageFileException("사용자 이미지를 등록해주세요."));
		verifyNotEmptyStringValue(file.getOriginalFilename(), new InvalidImageFileException("사용자 이미지를 등록해주세요."));
		verifyIsImageFile(file, new InvalidImageFileException("사용자 이미지는 png, jpg, jpeg 파일만 업로드 가능합니다."));
	}
}
