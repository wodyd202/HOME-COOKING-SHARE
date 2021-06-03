package com.homecookingshare.command.recipe.infra.validator;

import org.springframework.stereotype.Component;

import com.homecookingshare.command.recipe.model.RecipeCommand.ChangeServing;

@Component
public class ChangeServingValidator extends AbstractRecipeValidator<ChangeServing>{

	@Override
	public void validate(ChangeServing obj) {
		servingValidation(obj.getServing());
	}

}
