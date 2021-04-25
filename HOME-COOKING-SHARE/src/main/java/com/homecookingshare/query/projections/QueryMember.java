package com.homecookingshare.query.projections;

import java.io.Serializable;
import java.util.Date;

import com.homecookingshare.command.member.entity.Member;
import com.homecookingshare.command.member.values.AuthType;
import com.homecookingshare.command.member.values.MemberRole;
import com.homecookingshare.command.member.values.MemberState;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QueryMember implements Serializable{
	private static final long serialVersionUID = 1L;
	private String email;
	private String password;
	private String nickName;
	private String img;
	private MemberRole role;
	private AuthType authType;
	private MemberState state;
	private Date createDateTime;

	public void authSuccess() {
		this.authType = AuthType.YES;
	}

	public void changeProfileImage(String fileName) {
		this.img = fileName;
	}
	
	public QueryMember(Member member) {
		this.email = member.getEmail().getValue();
		this.password = member.getPassword().getValue();
		this.nickName = member.getProfile().getNickName();
		this.img =member.getProfile().getImg();
		this.role = member.getRole();
		this.authType = member.getAuthType();
		this.state = member.getState();
		this.createDateTime = member.getCreateDateTime();
	}

}
