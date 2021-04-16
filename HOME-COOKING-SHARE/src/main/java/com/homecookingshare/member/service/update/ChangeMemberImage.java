package com.homecookingshare.member.service.update;

import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ChangeMemberImage {
	private String changer;
	private String imgPath;
	
	@JsonIgnore
	private MultipartFile image;
	
	public String createImageName() {
		this.imgPath = UUID.randomUUID().toString();
		return this.imgPath;
	}
}
