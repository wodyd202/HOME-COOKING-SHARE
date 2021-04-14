package com.homecookingshare.member.service.update;

import org.springframework.web.multipart.MultipartFile;

import com.homecookingshare.common.Validator;
import com.homecookingshare.member.aggregate.exception.InvalidMemberException;

public class UpdateMemberValidator implements Validator<ChangeImageMember> {

	@Override
	public void validate(ChangeImageMember obj) {
		MultipartFile img = obj.getImg();
		verfyNotNullObject(img, new InvalidMemberException("사용자 이미지를 넣어주세요.", "img"));
		verfyImage(img);
	}

	private void verfyImage(MultipartFile img) {
		String name = img.getName();
		String extention = name.substring(name.indexOf("."), name.length()).toUpperCase();
		if (!(extention.equals(".PNG") || extention.equals(".JPG") || extention.equals(".JPEG"))) {
			throw new InvalidMemberException("사용자 이미지는 png, jpg, jpeg 형식의 파일만 등록할 수 있습니다.", "img");
		}
	}
}
