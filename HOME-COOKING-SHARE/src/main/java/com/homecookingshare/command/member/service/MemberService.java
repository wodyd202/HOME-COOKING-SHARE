package com.homecookingshare.command.member.service;

import com.homecookingshare.command.member.model.MemberCommand.ChangeImage;
import com.homecookingshare.command.member.model.MemberCommand.ChangePassword;
import com.homecookingshare.command.member.model.MemberCommand.RegisterMember;
import com.homecookingshare.common.Validator;
import com.homecookingshare.common.fileUpload.FileUploader;
import com.homecookingshare.domain.authKey.event.AuthSuccessed;
import com.homecookingshare.domain.member.Email;
import com.homecookingshare.domain.member.Member;

public interface MemberService {
	Member create(
			Validator<RegisterMember> validator, 
			RegisterMember command
		);
	
	Member changeImage(
			FileUploader fileUploader,
			Validator<ChangeImage> validator,
			Email targetUserEmail,
			ChangeImage command
		);

	Member changePassword(
			Validator<ChangePassword> validator, 
			Email targetUserEmail, 
			ChangePassword command
		);
	
	void authSuccess(
			AuthSuccessed event
		);
}
