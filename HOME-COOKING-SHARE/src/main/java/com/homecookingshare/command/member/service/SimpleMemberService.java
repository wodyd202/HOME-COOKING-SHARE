package com.homecookingshare.command.member.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.homecookingshare.command.member.exception.AlreadyDeletedMemberException;
import com.homecookingshare.command.member.exception.AlreadyExistMemberException;
import com.homecookingshare.command.member.exception.InvalidMemberException;
import com.homecookingshare.command.member.exception.MemberNotFoundException;
import com.homecookingshare.command.member.infra.JpaMemberRepository;
import com.homecookingshare.command.member.model.MemberCommand.ChangeImage;
import com.homecookingshare.command.member.model.MemberCommand.ChangePassword;
import com.homecookingshare.command.member.model.MemberCommand.RegisterMember;
import com.homecookingshare.command.recipe.exception.RecipeNotFoundException;
import com.homecookingshare.common.Validator;
import com.homecookingshare.common.fileUpload.FileUploader;
import com.homecookingshare.domain.authKey.event.AuthSuccessed;
import com.homecookingshare.domain.member.Email;
import com.homecookingshare.domain.member.Member;
import com.homecookingshare.domain.recipe.RecipeId;
import com.homecookingshare.domain.recipe.read.Recipe;
import com.homecookingshare.query.recipe.infra.RecipeReadRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SimpleMemberService implements MemberService {
	private PasswordEncoder passwordEncoder;
	private JpaMemberRepository memberRepository;
	private RecipeReadRepository recipeRepository;
	
	@Override
	public Member create(
			Validator<RegisterMember> validator, 
			RegisterMember command
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
		Member member = Member.create(passwordEncoder, command);
		memberRepository.save(member);
		return member;
	}

	@Override
	public Member changeImage(
			FileUploader fileUploader, 
			Validator<ChangeImage> validator, 
			Email targetUserEmail,
			ChangeImage command
		) {
		validator.validate(command);
		Member findMember = memberRepository.findById(targetUserEmail).orElseThrow(()->
				new MemberNotFoundException("해당 이메일의 유저가 존재하지 않습니다."));
		
		String saveFileName = encodeFileName(command.getFile());
		findMember.changeImage(saveFileName);
		fileUploader.uploadFile(command.getFile(), saveFileName);
		return memberRepository.save(findMember);
	}
	
	private String encodeFileName(MultipartFile file) {
		String name = file.getOriginalFilename();
		int lastIndexOf = name.lastIndexOf(".");
		return  UUID.randomUUID() + name.substring(lastIndexOf, name.length()).toUpperCase();
	}

	@Override
	public Member changePassword(
			Validator<ChangePassword> validator, 
			Email targetUserEmail, 
			ChangePassword command
		) {
		validator.validate(command);
		Member findMember = memberRepository.findById(targetUserEmail).orElseThrow(()->
			new MemberNotFoundException("해당 이메일의 유저가 존재하지 않습니다."));

		if(!passwordEncoder.matches(command.getOriginPassword(), findMember.getPassword().getValue())) {
			throw new InvalidMemberException("기존 비밀번호가 일치하지 않습니다.");
		}
		if(passwordEncoder.matches(command.getChangePassword(), findMember.getPassword().getValue())) {
			throw new InvalidMemberException("변경하고자 하는 비밀번호가 기존 비밀번호와 동일합니다.");
		}
		findMember.changePassword(passwordEncoder, command.getChangePassword());
		memberRepository.save(findMember);
		return findMember;
	}
	
	@EventListener
	@Override
	public void authSuccess(
			AuthSuccessed event
		) {
		Member member = memberRepository.findById(new Email(event.getTargetEmail().getEmail())).get();
		member.authSuccess();
	}

	@Override
	public void interestRecipe(
			Email targetMemberEmail, 
			RecipeId targetRecipeId
		) {
		Member findMember = memberRepository.findById(targetMemberEmail).orElseThrow(()->
			new MemberNotFoundException("해당 이메일의 유저가 존재하지 않습니다."));
		if(findMember.isDeleted()) {
			throw new AlreadyDeletedMemberException("이미 탈퇴한 회원입니다.");
		}
		Recipe findRecipe = recipeRepository.findByRecipeId(targetRecipeId).orElseThrow(()->new RecipeNotFoundException("해당 레시피가 존재하지 않습니다."));
		findMember.interestRecipe(findRecipe);
		memberRepository.save(findMember);
	}
	
}
