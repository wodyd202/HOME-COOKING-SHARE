package com.homecookingshare.member.aggregate.event;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.homecookingshare.common.event.BaseEvent;
import com.homecookingshare.member.Email;
import com.homecookingshare.member.aggregate.PayloadConverter;

import lombok.Getter;

@Getter
@Entity
@Table(name = "member_event", indexes = @Index(columnList = "id"))
public class MemberEvent extends BaseEvent {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long seq;

	@Embedded
	@AttributeOverride(name = "value", column = @Column(name = "id", length = 100, nullable = false))
	private Email id;

	@Lob
	@Column(nullable = false, name = "payload")
	@Convert(converter = PayloadConverter.class)
	private Object payload;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, name = "type", length = 20)
	private MemberEventType eventType;

	public MemberEvent(Email id, Object source, MemberEventType type) {
		super(source);
		this.eventType = type;
		this.id = id;
		this.payload = source;
	}

	public enum MemberEventType {
		CREATE, DELETE, CHANGE_IMAGE, UPDATE
	}
}
