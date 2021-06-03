package com.homecookingshare.recipe;

import com.homecookingshare.command.recipe.infra.validator.AbstractRecipeValidator;
import com.homecookingshare.command.recipe.model.RecipeCommand.AddMaterial;
import com.homecookingshare.command.recipe.model.RecipeCommand.RemoveMaterial;

public class RemoveMaterialValidator extends AbstractRecipeValidator<RemoveMaterial> {

	@Override
	public void validate(RemoveMaterial obj) {
		materialValidation(new AddMaterial(obj.getName(), obj.getCapacity()));
	}

}
