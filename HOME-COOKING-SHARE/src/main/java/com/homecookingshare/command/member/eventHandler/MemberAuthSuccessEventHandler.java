package com.homecookingshare.command.member.eventHandler;

import org.springframework.context.ApplicationListener;
import org.springframework.transaction.annotation.Transactional;

import com.homecookingshare.command.member.entity.Member;
import com.homecookingshare.command.member.infrastructure.MemberRepository;
import com.homecookingshare.command.member.service.update.MemberAuthSuccessEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class MemberAuthSuccessEventHandler implements ApplicationListener<MemberAuthSuccessEvent> {
	private final MemberRepository memberRepository;
	
	@Override
	@Transactional
	public void onApplicationEvent(MemberAuthSuccessEvent event) {
		Member member = memberRepository.findByEmail(event.getEmail()).get();
		member.authSuccess();
		log.info("auth success {}", event.getEmail());
	}

}
