package com.homecookingshare.domain.recipe.event;

import com.homecookingshare.domain.recipe.RecipeId;

import lombok.Getter;

@Getter
public class AbstractRecipeEvent {
	protected RecipeId targetId;
}
