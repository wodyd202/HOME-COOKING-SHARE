package com.homecookingshare.domain.recipe;

import java.io.Serializable;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecipeMainImage implements Serializable{
	private static final long serialVersionUID = 1L;
	private String path;

	public RecipeMainImage(MultipartFile mainImage) {
		this.path = UUID.randomUUID() + getFileExtention(mainImage);
	}

	private String getFileExtention(MultipartFile file) {
		String name = file.getOriginalFilename();
		int lastIndexOf = name.lastIndexOf(".");
		return name.substring(lastIndexOf, name.length()).toUpperCase();
	}
}
