package com.homecookingshare.command.member.service.update;

import org.springframework.context.ApplicationEvent;

import com.homecookingshare.command.member.values.Email;

import lombok.Getter;

@Getter
public class MemberAuthSuccessEvent extends ApplicationEvent {
	private static final long serialVersionUID = 1L;
	private Email email;
	
	public MemberAuthSuccessEvent(Object source) {
		super(source);
		this.email = (Email) source;
	}
}
