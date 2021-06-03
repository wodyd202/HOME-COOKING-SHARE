package com.homecookingshare.recipe;

import com.homecookingshare.command.recipe.infra.validator.AbstractRecipeValidator;
import com.homecookingshare.command.recipe.model.RecipeCommand.ChangeServing;

public class ChangeServingValidator extends AbstractRecipeValidator<ChangeServing>{

	@Override
	public void validate(ChangeServing obj) {
		servingValidation(obj.getServing());
	}

}
