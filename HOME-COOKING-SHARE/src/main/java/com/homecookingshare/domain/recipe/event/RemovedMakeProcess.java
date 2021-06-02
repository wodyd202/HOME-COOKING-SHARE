package com.homecookingshare.domain.recipe.event;

import com.homecookingshare.domain.recipe.MakeProcesses;
import com.homecookingshare.domain.recipe.RecipeId;

import lombok.Getter;

@Getter
public class RemovedMakeProcess extends AbstractRecipeEvent {
	private final MakeProcesses makeProcesses;
	public RemovedMakeProcess(RecipeId id, MakeProcesses makeProcesses) {
		this.targetId = id;
		this.makeProcesses = makeProcesses;
	}
}
