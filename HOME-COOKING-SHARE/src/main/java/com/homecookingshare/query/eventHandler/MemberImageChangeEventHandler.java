package com.homecookingshare.query.eventHandler;

import org.springframework.context.ApplicationListener;
import org.springframework.transaction.annotation.Transactional;

import com.homecookingshare.command.member.entity.Member;
import com.homecookingshare.command.member.events.MemberImageChangeEvent;
import com.homecookingshare.query.infrastructure.MemberQueryRepository;
import com.homecookingshare.query.projections.QueryMember;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberImageChangeEventHandler implements ApplicationListener<MemberImageChangeEvent> {
	private final MemberQueryRepository memberQueryRepository;
	
	@Override
	@Transactional
	public void onApplicationEvent(MemberImageChangeEvent event) {
		Member changeMember = event.getMember();
		QueryMember queryMember = memberQueryRepository.findByEmail(changeMember.getEmail().getValue()).get();
		queryMember.changeProfileImage(changeMember.getProfile().getImg());
	}

}
