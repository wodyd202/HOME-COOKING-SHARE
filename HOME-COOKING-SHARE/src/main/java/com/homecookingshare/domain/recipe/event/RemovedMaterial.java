package com.homecookingshare.domain.recipe.event;

import com.homecookingshare.domain.recipe.Materials;
import com.homecookingshare.domain.recipe.RecipeId;

import lombok.Getter;

@Getter
public class RemovedMaterial extends AbstractRecipeEvent{
	private final Materials materials;
	public RemovedMaterial(RecipeId id, Materials materials) {
		this.targetId = id;
		this.materials = materials;
	}
}
