package com.homecookingshare.command.member.service.update;

import com.homecookingshare.command.member.exception.InvalidMemberException;
import com.homecookingshare.common.Validator;

public class MemberImageChangeValidator implements Validator<MemberImageChangeCommand>{

	@Override
	public void validate(MemberImageChangeCommand obj) {
		verfyNotNullObject(obj.getFile(), new InvalidMemberException("사용자 이미지를 등록해주세요.", "file"));
		verfyNotEmptyStringValue(obj.getFile().getOriginalFilename(), new InvalidMemberException("사용자 이미지를 등록해주세요.", "file"));
		verfyIsImageFile(obj.getFile(), new InvalidMemberException("사용자 이미지는 png, jpg, jpeg 파일만 업로드 가능합니다.", "file"));
	}

}
