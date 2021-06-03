package com.homecookingshare.recipe;

import com.homecookingshare.command.recipe.infra.validator.AbstractRecipeValidator;
import com.homecookingshare.command.recipe.model.RecipeCommand.ChangeMainImage;

public class ChangeMainImageValidator extends AbstractRecipeValidator<ChangeMainImage>{

	@Override
	public void validate(ChangeMainImage obj) {
		mainImageValidation(obj.getFile());
	}

}
