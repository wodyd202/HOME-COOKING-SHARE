package com.homecookingshare.member.infrastructure;

import java.util.List;

import com.homecookingshare.member.Email;
import com.homecookingshare.member.aggregate.event.MemberEvent;

public interface MemberEventRepository {
	void save(MemberEvent entity);

	List<MemberEvent> findByEmail(Email email);
}
