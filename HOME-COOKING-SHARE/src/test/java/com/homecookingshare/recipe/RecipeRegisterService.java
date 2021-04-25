package com.homecookingshare.recipe;

import com.homecookingshare.common.AbstractRegisterService;
import com.homecookingshare.common.Validator;

public class RecipeRegisterService extends AbstractRegisterService<RegisterRecipe> {
	private final RecipeRepository recipeRepository;
	
	@Override
	protected void save(RegisterRecipe obj) {
		recipeRepository.save(obj.toEntity(Recipe.createId()));
	}
	
	public RecipeRegisterService(Validator<RegisterRecipe> validate, RecipeRepository recipeRepository) {
		super(validate);
		this.recipeRepository = recipeRepository;
	}

}
