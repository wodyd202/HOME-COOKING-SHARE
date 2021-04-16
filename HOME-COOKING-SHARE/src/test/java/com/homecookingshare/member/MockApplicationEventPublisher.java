package com.homecookingshare.member;

import org.springframework.context.ApplicationEventPublisher;

import com.homecookingshare.member.aggregate.event.MemberEvent;
import com.homecookingshare.member.service.eventListener.MemberCommandListener;
import com.homecookingshare.member.service.eventListener.MemberQueryListener;

public class MockApplicationEventPublisher implements ApplicationEventPublisher {

	private final MemberCommandListener memberCommandListener;
	private final MemberQueryListener memberQueryListener;

	@Override
	public void publishEvent(Object event) {
		memberCommandListener.onApplicationEvent((MemberEvent) event);
		memberQueryListener.onApplicationEvent((MemberEvent) event);
	}

	public MockApplicationEventPublisher(MemberCommandListener memberCommandListener,
			MemberQueryListener memberQueryListener) {
		this.memberCommandListener = memberCommandListener;
		this.memberQueryListener = memberQueryListener;
	}

}
