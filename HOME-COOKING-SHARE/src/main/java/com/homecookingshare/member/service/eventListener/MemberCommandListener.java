package com.homecookingshare.member.service.eventListener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.homecookingshare.member.aggregate.event.MemberEvent;
import com.homecookingshare.member.infrastructure.MemberEventRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberCommandListener implements ApplicationListener<MemberEvent> {

	private final MemberEventRepository eventStore;

	@Override
	@EventListener
	public void onApplicationEvent(MemberEvent event) {
		eventStore.save(event);
	}

}
