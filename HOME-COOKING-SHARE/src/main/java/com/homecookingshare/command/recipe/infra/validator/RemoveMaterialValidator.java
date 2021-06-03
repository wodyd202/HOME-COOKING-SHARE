package com.homecookingshare.command.recipe.infra.validator;

import org.springframework.stereotype.Component;

import com.homecookingshare.command.recipe.model.RecipeCommand.AddMaterial;
import com.homecookingshare.command.recipe.model.RecipeCommand.RemoveMaterial;

@Component
public class RemoveMaterialValidator extends AbstractRecipeValidator<RemoveMaterial> {

	@Override
	public void validate(RemoveMaterial obj) {
		materialValidation(new AddMaterial(obj.getName(), obj.getCapacity()));
	}

}
