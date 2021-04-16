package com.homecookingshare.member.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.homecookingshare.config.security.oauth.LoginUser;
import com.homecookingshare.member.aggregate.Member;
import com.homecookingshare.member.service.register.MemberRegisterService;
import com.homecookingshare.member.service.register.RegisterMember;
import com.homecookingshare.member.service.update.ChangeMemberImage;
import com.homecookingshare.member.service.update.MemberChangeImageService;

import lombok.RequiredArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("api/v1/member")
@RequiredArgsConstructor
public class MemberUpdateAPI {
	private final MemberRegisterService memberRegisterService;
	private final MemberChangeImageService memberChangeImageService; 
	
	@PostMapping
	public ResponseEntity<RegisterMember> register(@RequestBody RegisterMember registerMember){
		memberRegisterService.register(registerMember);
		return new ResponseEntity<>(registerMember,HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<ChangeMemberImage> changeMemberImage(@RequestParam MultipartFile image, @ApiIgnore @LoginUser Member member){
		ChangeMemberImage changeMemberImage = ChangeMemberImage
				.builder()
				.changer(member.getEmail().toString())
				.image(image)
				.build();
		memberChangeImageService.update(changeMemberImage);
		return new ResponseEntity<>(changeMemberImage, HttpStatus.OK);
	}
}
