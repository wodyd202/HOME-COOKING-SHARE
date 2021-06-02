package com.homecookingshare.domain.recipe;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Embeddable;

import org.springframework.web.multipart.MultipartFile;

import com.homecookingshare.command.recipe.model.RecipeCommand.AddMakeProcess;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class MakeProcess implements Serializable {
	private static final long serialVersionUID = 1L;
	private String imagePath;
	private String content;
	private int processOrder;
	
	public MakeProcess(AddMakeProcess makeProcess) {
		this.content = makeProcess.getContent();
		this.processOrder = makeProcess.getProcessOrder();
		this.imagePath = UUID.randomUUID() + getFileExtention(makeProcess.getFile());
	}

	private String getFileExtention(MultipartFile file) {
		String name = file.getOriginalFilename();
		int lastIndexOf = name.lastIndexOf(".");
		return name.substring(lastIndexOf, name.length()).toUpperCase();
	}

	public void increasmentProccessOrder() {
		this.processOrder = this.processOrder + 1;
	}

	public void decreasmentProccessOrder() {
		this.processOrder = this.processOrder - 1;
	}
}
