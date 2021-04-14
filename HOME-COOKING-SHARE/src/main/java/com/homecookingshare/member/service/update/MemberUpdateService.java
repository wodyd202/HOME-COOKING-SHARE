package com.homecookingshare.member.service.update;

import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.homecookingshare.common.AbstractUpdateService;
import com.homecookingshare.common.Validator;
import com.homecookingshare.member.Email;
import com.homecookingshare.member.aggregate.Member;
import com.homecookingshare.member.infrastructure.MemberRepository;

public class MemberUpdateService extends AbstractUpdateService<ChangeImageMember>{

	private final MemberRepository memberRepository;
	
	@Override
	protected void update(ChangeImageMember obj) {
		Member member = memberRepository.findByEmail(new Email(obj.getEmail())).get();
		String saveFileName = saveProfileImage(obj.getImg());
		member.changeProfileImage(saveFileName);
	}
	
	private String saveProfileImage(MultipartFile file) {
		String saveFileName = UUID.randomUUID().toString();
		return saveFileName;
	}
	
	public MemberUpdateService(Validator<ChangeImageMember> validate, MemberRepository memberRepository) {
		super(validate);
		this.memberRepository = memberRepository;
	}

}
