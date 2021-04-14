package com.homecookingshare.member.infrastructure;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.homecookingshare.member.Email;
import com.homecookingshare.member.aggregate.Member;
import com.homecookingshare.member.aggregate.QMember;
import com.querydsl.jpa.impl.JPAQuery;

@Repository
public class SimpleMemberRepository implements MemberRepository{

	@PersistenceContext
	private EntityManager em;
	
	private QMember member = QMember.member;
	
	@Override
	public Optional<Member> findByEmail(Email email) {
		JPAQuery<Member> query = new JPAQuery<>(em);
		Member findMember = query.from(member).where(member.email.eq(email)).fetchOne();
		return Optional.ofNullable(findMember);
	}

	@Override
	public void save(Member entity) {
		em.persist(entity);
	}

}
