package com.homecookingshare.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.homecookingshare.member.aggregate.event.MemberEvent;
import com.homecookingshare.member.infrastructure.MemberEventRepository;

public class FakeMemberEventRepository implements MemberEventRepository {
	private final Map<String, List<MemberEvent>> repository = new HashMap<>();

	@Override
	public void save(MemberEvent entity) {
		String email = entity.getId().toString();
		List<MemberEvent> baseEvent = repository.get(email);
		if(baseEvent == null) {
			repository.put(email, new ArrayList<>());
			baseEvent = repository.get(email);
		}
		baseEvent.add(entity);
	}

	@Override
	public List<MemberEvent> findByEmail(Email email) {
		List<MemberEvent> list = repository.get(email.toString());
		return list == null ? new ArrayList<>() : list;
	}

}
