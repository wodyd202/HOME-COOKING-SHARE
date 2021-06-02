package com.homecookingshare.domain.recipe.event;

import com.homecookingshare.domain.recipe.RecipeId;
import com.homecookingshare.domain.recipe.Recipe.Serving;

import lombok.Getter;

@Getter
public class ChangedServing extends AbstractRecipeEvent{
	private final Serving serving;
	public ChangedServing(RecipeId id, Serving serving) {
		this.targetId = id;
		this.serving = serving;
	}
}
