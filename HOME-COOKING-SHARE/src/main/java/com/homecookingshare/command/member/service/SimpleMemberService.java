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
				throw new AlreadyDeletedMemberException("?????? ????????? ???????????????.");
			}else {
				throw new AlreadyExistMemberException("?????? ?????? ???????????? ????????? ????????? ???????????????.");
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
				new MemberNotFoundException("?????? ???????????? ????????? ???????????? ????????????."));
		
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
			new MemberNotFoundException("?????? ???????????? ????????? ???????????? ????????????."));

		if(!passwordEncoder.matches(command.getOriginPassword(), findMember.getPassword().getValue())) {
			throw new InvalidMemberException("?????? ??????????????? ???????????? ????????????.");
		}
		if(passwordEncoder.matches(command.getChangePassword(), findMember.getPassword().getValue())) {
			throw new InvalidMemberException("??????????????? ?????? ??????????????? ?????? ??????????????? ???????????????.");
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
			new MemberNotFoundException("?????? ???????????? ????????? ???????????? ????????????."));
		if(findMember.isDeleted()) {
			throw new AlreadyDeletedMemberException("?????? ????????? ???????????????.");
		}
		Recipe findRecipe = recipeRepository.findByRecipeId(targetRecipeId).orElseThrow(()->new RecipeNotFoundException("?????? ???????????? ???????????? ????????????."));
		findMember.interestRecipe(findRecipe);
		memberRepository.save(findMember);
	}
	
}
