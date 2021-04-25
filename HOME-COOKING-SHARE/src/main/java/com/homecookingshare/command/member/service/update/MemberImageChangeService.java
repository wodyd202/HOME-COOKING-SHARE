package com.homecookingshare.command.member.service.update;

import java.util.UUID;

import org.springframework.context.ApplicationEventPublisher;

import com.homecookingshare.command.member.entity.Member;
import com.homecookingshare.command.member.events.MemberImageChangeEvent;
import com.homecookingshare.command.member.exception.MemberNotFoundException;
import com.homecookingshare.command.member.infrastructure.MemberRepository;
import com.homecookingshare.common.AbstractUpdateService;
import com.homecookingshare.common.Validator;
import com.homecookingshare.common.fileUpload.FileUploader;

public class MemberImageChangeService extends AbstractUpdateService<MemberImageChangeCommand> {
	private final MemberRepository memberRepository;
	private final FileUploader fileUploader;

	@Override
	protected void update(MemberImageChangeCommand obj) {
		Member member = memberRepository.findByEmail(obj.getEmail()).orElseThrow(()->new MemberNotFoundException("해당 이메일로 가입된 사용자가 존재하지 않습니다. 이메일을 다시 확인해주세요.", "email"));
		String fileName = UUID.randomUUID().toString();
		fileUploader.uploadFile(obj.getFile(), fileName);
		member.changeProfileImage(fileName);
		eventPublisher.publishEvent(new MemberImageChangeEvent(member));
	}

	public MemberImageChangeService(ApplicationEventPublisher publisher,Validator<MemberImageChangeCommand> validate, MemberRepository memberRepository,
			FileUploader fileUploader) {
		super(validate);
		super.eventPublisher = publisher;
		this.memberRepository = memberRepository;
		this.fileUploader = fileUploader;
	}

}
