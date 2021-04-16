package com.homecookingshare.member.service.update;

import org.springframework.web.multipart.MultipartFile;

import com.homecookingshare.common.Validator;
import com.homecookingshare.member.aggregate.exception.InvalidMemberException;
import com.homecookingshare.member.service.MemberValidator;

public class ChangeMemberImageValidator implements Validator<ChangeMemberImage>, MemberValidator {

	@Override
	public void validate(ChangeMemberImage obj) {
		String changer = obj.getChanger();
		MultipartFile image = obj.getImage();
		verfyNotEmptyStringValue(changer, new InvalidMemberException("프로필 사진을 바꿀 이메일을 입력해주세요.", "email"));
		verfyNotNullObject(image, new InvalidMemberException("변경할 프로필 사진을 등록해주세요.", "image"));
		verfyIsImageFile(image, new InvalidMemberException("변경할 프로필 사진을 등록해주세요.", "image"));
	}

}
