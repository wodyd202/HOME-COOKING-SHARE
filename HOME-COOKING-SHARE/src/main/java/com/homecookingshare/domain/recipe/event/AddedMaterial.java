package com.homecookingshare.domain.recipe.event;

import com.homecookingshare.domain.recipe.Material;
import com.homecookingshare.domain.recipe.RecipeId;

import lombok.Getter;

@Getter
public class AddedMaterial extends AbstractRecipeEvent {
	private final Material material;
	public AddedMaterial(RecipeId id, Material material) {
		this.targetId = id;
		this.material = material;
	}
}
