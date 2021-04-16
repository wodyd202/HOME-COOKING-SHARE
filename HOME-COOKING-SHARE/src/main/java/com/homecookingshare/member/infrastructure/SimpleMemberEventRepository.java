package com.homecookingshare.member.infrastructure;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.homecookingshare.member.Email;
import com.homecookingshare.member.aggregate.event.MemberEvent;
import com.homecookingshare.member.aggregate.event.QMemberEvent;
import com.querydsl.jpa.impl.JPAQuery;

@Repository
public class SimpleMemberEventRepository implements MemberEventRepository{

	@PersistenceContext
	private EntityManager em;
	
	private QMemberEvent memberEvent = QMemberEvent.memberEvent;
	
	@Override
	public void save(MemberEvent entity) {
		em.persist(entity);
	}

	@Override
	public List<MemberEvent> findByEmail(Email email) {
		JPAQuery<MemberEvent> query = new JPAQuery<>(em);
		return query.from(memberEvent).where(memberEvent.id().eq(email)).fetch();
	}

}
