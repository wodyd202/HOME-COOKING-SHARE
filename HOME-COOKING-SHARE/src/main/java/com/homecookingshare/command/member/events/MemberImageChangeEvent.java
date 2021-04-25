package com.homecookingshare.command.member.events;

import org.springframework.context.ApplicationEvent;

import com.homecookingshare.command.member.entity.Member;

import lombok.Getter;

@Getter
public class MemberImageChangeEvent extends ApplicationEvent {
	private static final long serialVersionUID = 1L;
	private Member member;
	
	public MemberImageChangeEvent(Object source) {
		super(source);
		this.member = (Member) source;
	}

}
