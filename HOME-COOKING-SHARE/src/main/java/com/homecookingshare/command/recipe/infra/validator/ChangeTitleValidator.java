package com.homecookingshare.command.recipe.infra.validator;

import org.springframework.stereotype.Component;

import com.homecookingshare.command.recipe.model.RecipeCommand.ChangeTitle;

@Component
public class ChangeTitleValidator extends AbstractRecipeValidator<ChangeTitle>{

	@Override
	public void validate(ChangeTitle obj) {
		titleValidation(obj.getTitle());
	}

}
