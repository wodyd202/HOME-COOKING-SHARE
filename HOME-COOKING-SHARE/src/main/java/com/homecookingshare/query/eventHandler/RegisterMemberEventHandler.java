package com.homecookingshare.query.eventHandler;

import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.event.TransactionalEventListener;

import com.homecookingshare.command.member.events.RegisterMemberEvent;
import com.homecookingshare.query.infrastructure.MemberQueryRepository;
import com.homecookingshare.query.projections.QueryMember;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class RegisterMemberEventHandler implements ApplicationListener<RegisterMemberEvent>{

	private final MemberQueryRepository memberQueryRepository;

	@Override
	@TransactionalEventListener
	@Order(1)
	public void onApplicationEvent(RegisterMemberEvent event) {
		QueryMember member = new QueryMember(event.getMember());
		memberQueryRepository.save(member);
		log.info("create member in redis {}", member);
	}

}
