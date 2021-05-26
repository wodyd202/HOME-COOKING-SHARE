package com.homecookingshare.command.member.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import com.homecookingshare.command.member.exception.AlreadyDeletedMemberException;
import com.homecookingshare.command.member.exception.AlreadyExistMemberException;
import com.homecookingshare.command.member.exception.InvalidMemberException;
import com.homecookingshare.command.member.exception.MemberNotFoundException;
import com.homecookingshare.command.member.infra.JpaMemberRepository;
import com.homecookingshare.command.member.service.MemberCommand.ChangeImageCommand;
import com.homecookingshare.command.member.service.MemberCommand.ChangePasswordCommand;
import com.homecookingshare.command.member.service.MemberCommand.EmailAuthenticationCommand;
import com.homecookingshare.command.member.service.MemberCommand.RegisterMemberCommand;
import com.homecookingshare.common.Validator;
import com.homecookingshare.common.fileUpload.FileUploader;
import com.homecookingshare.domain.member.Email;
import com.homecookingshare.domain.member.Member;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SimpleMemberService implements MemberService {
	private PasswordEncoder passwordEncoder;
	private JpaMemberRepository memberRepository;
	
	@Override
	public Member create(
			Validator<RegisterMemberCommand> validator, 
			RegisterMemberCommand command
		) {
		validator.validate(command);
		Optional<Member> findMember = memberRepository.findById(new Email(command.getEmail()));
		if(findMember.isPresent()) {
			Member existMember = findMember.get();
			if(existMember.isDeleted()) {
				throw new AlreadyDeletedMemberException("이미 탈퇴한 회원입니다.");
			}else {
				throw new AlreadyExistMemberException("이미 해당 이메일로 가입된 회원이 존재합니다.");
			}
		}
		Member member = Member.withRegisterMemberCommand(command);
		member.encodePassword(passwordEncoder);
		memberRepository.save(member);
		return member;
	}

	@Override
	public Member emailAuthentication(
			Validator<EmailAuthenticationCommand> validator, 
			Email authenticationTargetEmail,
			EmailAuthenticationCommand command
		) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Member changeImage(
			FileUploader fileUploader, 
			Validator<ChangeImageCommand> validator, 
			Email targetUserEmail,
			ChangeImageCommand command
		) {
		validator.validate(command);
		Member findMember = memberRepository.findById(targetUserEmail).orElseThrow(()->
				new MemberNotFoundException("해당 이메일의 유저가 존재하지 않습니다."));
		
		verifyAuthenticateMember(findMember);
		
		String saveFileName = encodeFileName(command.getFile());
		findMember.changeImage(saveFileName);
		fileUploader.uploadFile(command.getFile(), saveFileName);
		return memberRepository.save(findMember);
	}
	
	private String encodeFileName(MultipartFile file) {
		String name = file.getName();
		int lastIndexOf = name.lastIndexOf(".");
		return  UUID.randomUUID() + name.substring(lastIndexOf, name.length()).toUpperCase();
	}

	@Override
	public Member changePassword(
			Validator<ChangePasswordCommand> validator, 
			Email targetUserEmail, 
			ChangePasswordCommand command
		) {
		validator.validate(command);
		Member findMember = memberRepository.findById(targetUserEmail).orElseThrow(()->
			new MemberNotFoundException("해당 이메일의 유저가 존재하지 않습니다."));
		
		verifyAuthenticateMember(findMember);
		
		findMember.changePassword(command.getChangePassword());
		findMember.encodePassword(passwordEncoder);
		
		return memberRepository.save(findMember);
	}
	
	private void verifyAuthenticateMember(Member findMember) {
		if(findMember.isNotAuth()) {
			throw new InvalidMemberException("이메일 인증이 필요한 사용자 입니다.");
		}
	}
	
}
