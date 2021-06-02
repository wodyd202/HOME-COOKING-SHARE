package com.homecookingshare.command.recipe.infra;

import org.springframework.data.jpa.repository.JpaRepository;

import com.homecookingshare.domain.recipe.RecipeId;
import com.homecookingshare.domain.recipe.Recipe;

public interface JpaRecipeRepository extends JpaRepository<Recipe, RecipeId>{

}
