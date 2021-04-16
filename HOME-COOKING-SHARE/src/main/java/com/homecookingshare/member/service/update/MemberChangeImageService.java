package com.homecookingshare.member.service.update;

import org.springframework.context.ApplicationEventPublisher;

import com.homecookingshare.common.AbstractUpdateService;
import com.homecookingshare.common.fileUpload.FileUploader;
import com.homecookingshare.member.Email;
import com.homecookingshare.member.aggregate.Member;
import com.homecookingshare.member.aggregate.event.MemberEvent;
import com.homecookingshare.member.aggregate.event.MemberEvent.MemberEventType;
import com.homecookingshare.member.aggregate.exception.MemberNotFoundException;
import com.homecookingshare.member.infrastructure.MemberQueryRepository;

public class MemberChangeImageService extends AbstractUpdateService<ChangeMemberImage> {
	private MemberQueryRepository memberQueryRepository;
	private final FileUploader fileUploader;

	@Override
	public void update(ChangeMemberImage obj) {
		Email email = new Email(obj.getChanger());
		Member member = memberQueryRepository.findByEmail(email)
				.orElseThrow(() -> new MemberNotFoundException("해당 사용자가 존재하지 않습니다. 다시 확인해주세요.", "email"));
		String createFileName = obj.createImageName();
		member.changeProfileImage(createFileName);
		fileUploader.uploadFile(obj.getImage(), createFileName);
		eventPublisher.publishEvent(new MemberEvent(email, obj, MemberEventType.CHANGE_IMAGE));
	}

	public MemberChangeImageService(ChangeMemberImageValidator changeMemberImageValidator,
			MemberQueryRepository memberQueryRepository, FileUploader fileUploader,
			ApplicationEventPublisher publisher) {
		super(changeMemberImageValidator);
		super.eventPublisher = publisher;
		this.memberQueryRepository = memberQueryRepository;
		this.fileUploader = fileUploader;
	}

}
