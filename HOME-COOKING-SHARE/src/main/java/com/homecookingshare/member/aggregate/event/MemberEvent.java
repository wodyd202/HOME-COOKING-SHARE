package com.homecookingshare.member.aggregate.event;

import org.springframework.context.ApplicationEvent;

import com.homecookingshare.member.aggregate.Member;

import lombok.Getter;

@Getter
public class MemberEvent extends ApplicationEvent {
	private static final long serialVersionUID = 1L;
	private Member member;
	private MemberEventType eventType;
	
	public MemberEvent(Object source,MemberEventType eventType) {
		super(source);
		this.member = (Member) source;
		this.eventType = eventType;
	}

	public enum MemberEventType {
		CREATE, DELETE, CHANGE_IMAGE, UPDATE
	}
}
