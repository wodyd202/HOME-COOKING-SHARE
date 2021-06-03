package com.homecookingshare.domain.recipe.event;

import com.homecookingshare.domain.recipe.Materials;
import com.homecookingshare.domain.recipe.RecipeId;

import lombok.Getter;

@Getter
public class AddedMaterial extends AbstractRecipeEvent {
	private final Materials materials;

	public AddedMaterial(RecipeId id, Materials materials) {
		this.targetId = id;
		this.materials = materials;
	}
}
