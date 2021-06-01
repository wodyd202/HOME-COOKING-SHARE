package com.homecookingshare.command.memberEmailAuth.service;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.homecookingshare.command.member.exception.MemberNotFoundException;
import com.homecookingshare.command.member.infra.JpaMemberRepository;
import com.homecookingshare.command.memberEmailAuth.infra.JpaMemberEmailAuthRepository;
import com.homecookingshare.command.memberEmailAuth.model.MemberEmailAuthCommand.Verifycation;
import com.homecookingshare.common.Validator;
import com.homecookingshare.domain.authKey.MemberEmailAuthKey;
import com.homecookingshare.domain.authKey.TargetEmail;
import com.homecookingshare.domain.authKey.event.RegisterdEmailAuthKey;
import com.homecookingshare.domain.member.Email;
import com.homecookingshare.domain.member.event.RegisterdMember;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SimpleMemberEmailAuthService implements MemberEmailAuthService {
	private JpaMemberRepository jpaMemberRepository;
	private JpaMemberEmailAuthRepository jpaMemberEmailAuthRepository;

	@Override
	@EventListener
	public void create(
			RegisterdMember event
		) {
		MemberEmailAuthKey create = MemberEmailAuthKey.create(event.getTargetUserEmail());
		jpaMemberEmailAuthRepository.save(create);
	}

	@Override
	@EventListener
	public void create(
			RegisterdEmailAuthKey event
		) {
		MemberEmailAuthKey create = MemberEmailAuthKey.create(new Email(event.getTargetEmail().getEmail()));
		jpaMemberEmailAuthRepository.save(create);
	}

	@Override
	public void verifycation(
			Validator<Verifycation> validator, 
			Verifycation command
		) {
		validator.validate(command);
		if(!jpaMemberRepository.existsById(new Email(command.getRequireVerificationTargetEmail()))) {
			throw new MemberNotFoundException("해당 이메일의 회원이 존재하지 않습니다.");
		}
		MemberEmailAuthKey memberEmailAuthKey = jpaMemberEmailAuthRepository.findTop1ByEmailOrderBySeqDesc(new TargetEmail(command.getRequireVerificationTargetEmail())).get();
		memberEmailAuthKey.verifycation(command);
		jpaMemberEmailAuthRepository.save(memberEmailAuthKey);
	}

}
