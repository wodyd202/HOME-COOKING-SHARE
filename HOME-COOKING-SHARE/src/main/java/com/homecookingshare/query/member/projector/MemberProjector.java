package com.homecookingshare.query.member.projector;

import java.util.Date;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.homecookingshare.domain.member.AuthType;
import com.homecookingshare.domain.member.MemberRule;
import com.homecookingshare.domain.member.MemberState;
import com.homecookingshare.domain.member.event.RegisterdMember;
import com.homecookingshare.domain.member.read.Member;
import com.homecookingshare.query.member.infra.MemberReadRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class MemberProjector {
	private MemberReadRepository memberReadRepository;
	
	@EventListener
	protected void on(RegisterdMember event) {
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
}
