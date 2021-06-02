package com.homecookingshare.domain.recipe.event;

import com.homecookingshare.domain.recipe.RecipeId;
import com.homecookingshare.domain.recipe.Recipe.Level;

import lombok.Getter;

@Getter
public class ChangedLevel extends AbstractRecipeEvent{
	private Level level;
	public ChangedLevel(RecipeId id, Level level) {
		this.targetId = id;
		this.level = level;
	}
}
