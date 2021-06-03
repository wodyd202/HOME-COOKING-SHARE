package com.homecookingshare.command.recipe.infra.validator;

import org.springframework.stereotype.Component;

import com.homecookingshare.command.recipe.model.RecipeCommand.AddMaterial;

@Component
public class AddMaterialValidator extends AbstractRecipeValidator<AddMaterial> {

	@Override
	public void validate(AddMaterial obj) {
		materialValidation(obj);
	}

}
