package com.homecookingshare.command.member.config;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.homecookingshare.command.member.entity.Member;
import com.homecookingshare.command.member.entity.QMember;
import com.homecookingshare.command.member.infrastructure.MemberRepository;
import com.homecookingshare.command.member.values.Email;
import com.querydsl.jpa.impl.JPAQuery;

@Repository
public class SimpleMemberRepository implements MemberRepository{
	
	@PersistenceContext
	private EntityManager em;

	private QMember member = QMember.member;
	
	@Override
	public Member save(Member entity) {
		em.persist(entity);
		return entity;
	}

	@Override
	public Optional<Member> findByEmail(Email email) {
		JPAQuery<Member> query = new JPAQuery<>(em);
		return Optional.ofNullable(query.from(member).where(member.email.eq(email)).fetchOne());
	}

}
