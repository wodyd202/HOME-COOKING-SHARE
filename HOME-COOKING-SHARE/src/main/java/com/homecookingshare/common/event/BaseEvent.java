package com.homecookingshare.common.event;

import java.util.Date;

import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;

@Getter
@MappedSuperclass
public class BaseEvent extends ApplicationEvent{
	private static final long serialVersionUID = 1L;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	public BaseEvent(Object source) {
		super(source);
		this.createDate = new Date();
	}
}
