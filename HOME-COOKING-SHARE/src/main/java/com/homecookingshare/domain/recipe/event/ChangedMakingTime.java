package com.homecookingshare.domain.recipe.event;

import com.homecookingshare.domain.recipe.MakingTime;
import com.homecookingshare.domain.recipe.RecipeId;

import lombok.Getter;

@Getter
public class ChangedMakingTime extends AbstractRecipeEvent {
	private final MakingTime makingTime;

	public ChangedMakingTime(RecipeId id, MakingTime makingTime) {
		this.targetId = id;
		this.makingTime = makingTime;
	}
}
