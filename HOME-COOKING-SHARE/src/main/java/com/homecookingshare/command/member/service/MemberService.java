package com.homecookingshare.command.member.service;

import com.homecookingshare.command.member.model.MemberCommand.ChangeImageCommand;
import com.homecookingshare.command.member.model.MemberCommand.ChangePasswordCommand;
import com.homecookingshare.command.member.model.MemberCommand.RegisterMemberCommand;
import com.homecookingshare.common.Validator;
import com.homecookingshare.common.fileUpload.FileUploader;
import com.homecookingshare.domain.member.Email;
import com.homecookingshare.domain.member.Member;

public interface MemberService {
	Member create(
			Validator<RegisterMemberCommand> validator, 
			RegisterMemberCommand command
		);
	
	Member changeImage(
			FileUploader fileUploader,
			Validator<ChangeImageCommand> validator,
			Email targetUserEmail,
			ChangeImageCommand command
		);

	Member changePassword(
			Validator<ChangePasswordCommand> validator, 
			Email targetUserEmail, 
			ChangePasswordCommand command
		);

}
