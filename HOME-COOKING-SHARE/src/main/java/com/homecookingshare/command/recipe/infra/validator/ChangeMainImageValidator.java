package com.homecookingshare.command.recipe.infra.validator;

import org.springframework.stereotype.Component;

import com.homecookingshare.command.recipe.model.RecipeCommand.ChangeMainImage;

@Component
public class ChangeMainImageValidator extends AbstractRecipeValidator<ChangeMainImage>{

	@Override
	public void validate(ChangeMainImage obj) {
		mainImageValidation(obj.getFile());
	}

}
