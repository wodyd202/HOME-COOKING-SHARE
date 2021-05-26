package com.homecookingshare.command.member.infra;

import org.springframework.data.jpa.repository.JpaRepository;

import com.homecookingshare.domain.member.Email;
import com.homecookingshare.domain.member.Member;

public interface JpaMemberRepository extends JpaRepository<Member, Email>{

}
