package com.homecookingshare.recipe;

import com.homecookingshare.command.recipe.infra.validator.AbstractRecipeValidator;
import com.homecookingshare.command.recipe.model.RecipeCommand.AddMaterial;

public class AddMaterialValidator extends AbstractRecipeValidator<AddMaterial>{

	@Override
	public void validate(AddMaterial obj) {
		materialValidation(obj);
	}

}
