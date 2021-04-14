package com.homecookingshare.recipe;

import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import com.homecookingshare.recipe.Recipe.RecipeLevel;
import com.homecookingshare.recipe.Recipe.Serving;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegisterRecipe {
	private String writer;
	private String title;
	private MultipartFile mainImg;
	private String content;
	private RecipeLevel level;
	private Serving serving;
	private long time;
	private String category;
	private String tip;
	private Set<RegisterMaterial> material;

	public Recipe toEntity(RecipeId createId) {
		return new Recipe(createId, this);
	}
}
