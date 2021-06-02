package com.homecookingshare.domain.recipe.event;

import com.homecookingshare.domain.recipe.MakeProcesses;
import com.homecookingshare.domain.recipe.RecipeId;

import lombok.Getter;

@Getter
public class AddedMakeProcess extends AbstractRecipeEvent{
	private final MakeProcesses makeProcesses;
	public AddedMakeProcess(RecipeId id, MakeProcesses makeProcesses) {
		this.targetId = id;
		this.makeProcesses = makeProcesses;
	}
}
