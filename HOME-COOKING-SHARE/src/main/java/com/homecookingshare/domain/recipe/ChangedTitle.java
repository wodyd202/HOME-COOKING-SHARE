package com.homecookingshare.domain.recipe;

import com.homecookingshare.domain.recipe.event.AbstractRecipeEvent;

import lombok.Getter;

@Getter
public class ChangedTitle extends AbstractRecipeEvent{
	private final RecipeTitle title;
	public ChangedTitle(RecipeId id, RecipeTitle title) {
		this.targetId = id;
		this.title = title;
	}
}
