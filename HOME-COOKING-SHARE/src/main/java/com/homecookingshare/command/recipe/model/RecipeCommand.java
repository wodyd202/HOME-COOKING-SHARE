package com.homecookingshare.command.recipe.model;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.homecookingshare.domain.recipe.Recipe.Level;
import com.homecookingshare.domain.recipe.Recipe.RecipeCategory;
import com.homecookingshare.domain.recipe.Recipe.Serving;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class RecipeCommand {
	
	@Setter
	@Getter
	@AllArgsConstructor
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class AddMaterial {
		private String name;
		private String capacity;
	}
	
	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@AllArgsConstructor
	public static class RemoveMaterial {
		private String name;
		private String capacity;
	}
	
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class AddMakeProcess {
		private MultipartFile file;
		private String content;
		int processOrder;
	}
	
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class RegisterRecipe {
		private String title;
		private MultipartFile mainImage;
		private RecipeCategory category;
		private Level level;
		private Serving serving;
		private long makingTime;
		private List<AddMaterial> materials;
		private List<AddMakeProcess> makeProcesses;
	}
	
	@Getter
	@AllArgsConstructor
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class ChangeTitle {
		private String title;
	}
	
	@Getter
	@AllArgsConstructor
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class RemoveMakeProcess { 
		private int processOrder;
	}
	
	@Getter
	@AllArgsConstructor
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class ChangeLevel {
		private Level level;
	}
	
	@Getter
	@AllArgsConstructor
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class ChangeServing {
		private Serving serving;
	}
	
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class ChangeMainImage {
		private MultipartFile file;
	}
}
