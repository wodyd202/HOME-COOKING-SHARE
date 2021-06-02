package com.homecookingshare.domain.recipe.event;

import com.homecookingshare.domain.recipe.Material;
import com.homecookingshare.domain.recipe.RecipeId;

import lombok.Getter;

@Getter
public class RemovedMaterial extends AbstractRecipeEvent{
	private final Material material;
	public RemovedMaterial(RecipeId id, Material material) {
		this.targetId = id;
		this.material = material;
	}
}
