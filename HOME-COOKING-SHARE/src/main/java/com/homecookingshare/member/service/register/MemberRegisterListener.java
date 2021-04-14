package com.homecookingshare.member.service.register;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.homecookingshare.member.aggregate.event.MemberEvent;
import com.homecookingshare.member.aggregate.event.MemberEvent.MemberEventType;
import com.homecookingshare.member.infrastructure.SimpleMemberRedisRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberRegisterListener implements ApplicationListener<MemberEvent>{
	
	private final SimpleMemberRedisRepository memberRedisRepository;
	
	@Override
	public void onApplicationEvent(MemberEvent event) {
		if(event.getEventType() == MemberEventType.CREATE) {
			memberRedisRepository.save(event.getMember());
		}
	}

}
