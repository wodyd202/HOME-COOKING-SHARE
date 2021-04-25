package com.homecookingshare.command.member.api;

import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.homecookingshare.command.member.service.register.RegisterMemberCommand;
import com.homecookingshare.command.member.service.register.RegisterMemberService;
import com.homecookingshare.command.member.service.update.MemberImageChangeCommand;
import com.homecookingshare.command.member.service.update.MemberImageChangeService;
import com.homecookingshare.command.member.values.Email;
import com.homecookingshare.common.CustomResource;
import com.homecookingshare.config.security.oauth.LoginUser;
import com.homecookingshare.query.projections.QueryMember;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/member")
@Api(tags = "사용자 API")
public class MemberUpdateAPI {
	private final RegisterMemberService registerMemberService;
	private final MemberImageChangeService memberImageChangeService;
	
	@PostMapping
	@ApiOperation("사용자 생성")
	public ResponseEntity<Resource<RegisterMemberCommand>> save(
			@RequestBody 
			@ApiParam(name = "registerMemberCommand" ,required = true, value = "생성할 계정")
			RegisterMemberCommand command
		){
		registerMemberService.register(command);
		command.emptyPassword();
		CustomResource<RegisterMemberCommand> resource = new CustomResource<RegisterMemberCommand>(command, this.getClass());
		return new ResponseEntity<>(resource, HttpStatus.CREATED);
	}
	
	@PutMapping
	@ApiOperation("사용자 이미지 변경")
	public ResponseEntity<String> changeImage(
			@ApiParam(name = "file" ,required = true, value = "이미지 파일")
			MultipartFile file,
			@ApiIgnore
			@LoginUser QueryMember member){
		MemberImageChangeCommand command = new MemberImageChangeCommand(new Email(member.getEmail()), file);
		memberImageChangeService.register(command);
		return new ResponseEntity<>("사용자 이미지 변경 완료", HttpStatus.OK);
	}
	
}
