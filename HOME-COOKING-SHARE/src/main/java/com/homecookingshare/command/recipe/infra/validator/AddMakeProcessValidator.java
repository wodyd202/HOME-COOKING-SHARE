package com.homecookingshare.command.recipe.infra.validator;

import org.springframework.stereotype.Component;

import com.homecookingshare.command.recipe.model.RecipeCommand.AddMakeProcess;

@Component
public class AddMakeProcessValidator extends AbstractRecipeValidator<AddMakeProcess>{

	@Override
	public void validate(AddMakeProcess obj) {
		makeProcessValidation(obj);
	}

}
