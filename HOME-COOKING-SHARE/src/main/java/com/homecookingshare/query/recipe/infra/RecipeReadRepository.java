package com.homecookingshare.query.recipe.infra;

import java.util.Optional;

import com.homecookingshare.domain.recipe.RecipeId;
import com.homecookingshare.domain.recipe.read.Recipe;

public interface RecipeReadRepository {
	void save(Recipe recipe);

	Optional<Recipe> findByRecipeId(RecipeId targetId);
}
