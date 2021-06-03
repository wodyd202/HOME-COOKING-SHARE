package com.homecookingshare.domain.recipe.event;

import com.homecookingshare.domain.recipe.RecipeId;
import com.homecookingshare.domain.recipe.RecipeMainImage;

import lombok.Getter;

@Getter
public class ChangedMainImage extends AbstractRecipeEvent{
	private final RecipeMainImage mainImage;
	
	public ChangedMainImage(RecipeId id, RecipeMainImage mainImage) {
		this.targetId = id;
		this.mainImage = mainImage;
	}
}
