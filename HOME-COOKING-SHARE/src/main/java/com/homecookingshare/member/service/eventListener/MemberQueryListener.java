package com.homecookingshare.member.service.eventListener;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.homecookingshare.member.aggregate.Member;
import com.homecookingshare.member.aggregate.event.MemberEvent;
import com.homecookingshare.member.aggregate.event.MemberEvent.MemberEventType;
import com.homecookingshare.member.infrastructure.MemberQueryRepository;
import com.homecookingshare.member.service.update.ChangeMemberImage;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberQueryListener implements ApplicationListener<MemberEvent>{
	private final MemberQueryRepository memberRedisRepository;
	
	@Override
	@TransactionalEventListener
	public void onApplicationEvent(MemberEvent event) {
		MemberEventType eventType = event.getEventType();
		if(eventType == MemberEventType.CREATE) {
			memberRedisRepository.save((Member) event.getPayload());
		}
		if(eventType == MemberEventType.CHANGE_IMAGE) {
			Member member = memberRedisRepository.findByEmail(event.getId()).get();
			member.changeProfileImage(((ChangeMemberImage) event.getPayload()).getImgPath());
			memberRedisRepository.save(member);
		}
	}

}
