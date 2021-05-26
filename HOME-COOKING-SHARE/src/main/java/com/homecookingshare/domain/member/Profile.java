package com.homecookingshare.domain.member;

import java.io.Serializable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Profile implements Serializable{
	private static final long serialVersionUID = 1L;
	private String nickName;
	private String img;

	public Profile(String nickName) {
		this.nickName = nickName;
	}

	public void changeImage(String saveFileName) {
		this.img = saveFileName;
	}

}
