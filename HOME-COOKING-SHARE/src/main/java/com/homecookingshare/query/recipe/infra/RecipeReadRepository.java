package com.homecookingshare.query.recipe.infra;

import java.util.List;
import java.util.Optional;

import com.homecookingshare.domain.recipe.RecipeId;
import com.homecookingshare.domain.recipe.read.Recipe;
import com.homecookingshare.query.recipe.model.RecipeSearch;

public interface RecipeReadRepository {
	void save(Recipe recipe);

	Optional<Recipe> findByRecipeId(RecipeId targetId);
	
	List<Recipe> findAll(RecipeSearch dto);
}
