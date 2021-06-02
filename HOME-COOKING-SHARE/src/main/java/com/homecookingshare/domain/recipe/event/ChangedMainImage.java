package com.homecookingshare.domain.recipe.event;

import com.homecookingshare.domain.recipe.RecipeId;

import lombok.Getter;

@Getter
public class ChangedMainImage extends AbstractRecipeEvent{
	private final String mainImagePath;
	
	public ChangedMainImage(RecipeId id, String mainImagePath) {
		this.targetId = id;
		this.mainImagePath = mainImagePath;
	}
}
