package com.homecookingshare.command.memberEmailAuth.service;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.homecookingshare.command.member.exception.MemberNotFoundException;
import com.homecookingshare.command.member.infra.JpaMemberRepository;
import com.homecookingshare.command.memberEmailAuth.exception.InvalidEmailAuthenticationException;
import com.homecookingshare.command.memberEmailAuth.infra.JpaMemberEmailAuthRepository;
import com.homecookingshare.command.memberEmailAuth.model.MemberEmailAuthCommand.Verifycation;
import com.homecookingshare.common.Validator;
import com.homecookingshare.common.mail.MailUtil;
import com.homecookingshare.domain.authKey.MemberEmailAuthKey;
import com.homecookingshare.domain.authKey.TargetEmail;
import com.homecookingshare.domain.authKey.event.RegisterdEmailAuthKey;
import com.homecookingshare.domain.member.Email;
import com.homecookingshare.domain.member.Member;
import com.homecookingshare.domain.member.event.RegisterdMember;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SimpleMemberEmailAuthService implements MemberEmailAuthService {
	private JpaMemberRepository jpaMemberRepository;
	private JpaMemberEmailAuthRepository jpaMemberEmailAuthRepository;
	private MailUtil mailUtil;
	
	@Override
	@EventListener
	public void create(
			RegisterdMember event
		) {
		MemberEmailAuthKey create = MemberEmailAuthKey.create(event.getTargetUserEmail());
		mailUtil.sendEmail(event.getTargetUserEmail().getValue(), "[홈 쿠킹 쉐어] 인증 메일입니다.", create.getKey());
		jpaMemberEmailAuthRepository.save(create);
	}

	@Override
	@EventListener
	public void create(
			RegisterdEmailAuthKey event
		) {
		MemberEmailAuthKey create = MemberEmailAuthKey.create(new Email(event.getTargetEmail().getEmail()));
		mailUtil.sendEmail(event.getTargetEmail().getEmail(), "[홈 쿠킹 쉐어] 인증 메일입니다.", create.getKey());
		jpaMemberEmailAuthRepository.save(create);
	}

	@Override
	public void verifycation(
			Validator<Verifycation> validator, 
			Verifycation command
		) {
		validator.validate(command);
		Member findMember = jpaMemberRepository.findById(new Email(command.getRequireVerificationTargetEmail())).orElseThrow(()->
			new MemberNotFoundException("해당 이메일의 회원이 존재하지 않습니다.")
		);
		if(findMember.isAlreadyAuth()) {
			throw new InvalidEmailAuthenticationException("이미 이메일 인증한 회원입니다.");
		}
		MemberEmailAuthKey memberEmailAuthKey = jpaMemberEmailAuthRepository.findTop1ByEmailOrderBySeqDesc(new TargetEmail(command.getRequireVerificationTargetEmail())).get();
		try {
			memberEmailAuthKey.verifycation(command);
		}catch (InvalidEmailAuthenticationException e) {
			throw e;
		}finally {
			jpaMemberEmailAuthRepository.save(memberEmailAuthKey);
		} 
	}
}
