package com.homecookingshare.query.member.projector;

import java.util.Date;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.homecookingshare.domain.authKey.event.AuthSuccessed;
import com.homecookingshare.domain.member.AuthType;
import com.homecookingshare.domain.member.Email;
import com.homecookingshare.domain.member.MemberRule;
import com.homecookingshare.domain.member.MemberState;
import com.homecookingshare.domain.member.event.ChangedMemberImage;
import com.homecookingshare.domain.member.event.ChangedMemberPassword;
import com.homecookingshare.domain.member.event.RegisterdMember;
import com.homecookingshare.domain.member.read.Member;
import com.homecookingshare.query.member.infra.MemberReadRepository;

import lombok.RequiredArgsConstructor;

@Async
@Component
@Transactional
@RequiredArgsConstructor
public class SimpleMemberProjector implements MemberProjector{
	private final MemberReadRepository memberReadRepository;
	
	@EventListener
	public void on(RegisterdMember event) {
		Member member = Member
				.builder()
				.email(event.getTargetUserEmail())
				.password(event.getPassword())
				.profile(event.getProfile())
				.rule(MemberRule.MEMBER)
				.authType(AuthType.NO)
				.state(MemberState.CREATE)
				.createDateTime(new Date())
				.build();
		memberReadRepository.save(member);
	}
	
	@EventListener
	public void on(AuthSuccessed event) {
		Member member = memberReadRepository.findByEmail(new Email(event.getTargetEmail().getEmail())).get();
		member.authSuccess();
		memberReadRepository.save(member);
	}
	
	@EventListener
	public void on(ChangedMemberImage event) {
		Member member = memberReadRepository.findByEmail(event.getTargetUserEmail()).get();
		member.changeImage(event.getImageName());
		memberReadRepository.save(member);
	}
	
	@EventListener
	public void on(ChangedMemberPassword event) {
		Member member = memberReadRepository.findByEmail(event.getTargetUserEmail()).get();
		member.changePassword(event.getChangePassword());
		memberReadRepository.save(member);
	}
}
