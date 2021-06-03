package com.homecookingshare.command.recipe.infra.validator;

import org.springframework.stereotype.Component;

import com.homecookingshare.command.recipe.model.RecipeCommand.ChangeLevel;

@Component
public class ChangeLevelValidator extends AbstractRecipeValidator<ChangeLevel>{

	@Override
	public void validate(ChangeLevel obj) {
		levelValidation(obj.getLevel());
	}

}
