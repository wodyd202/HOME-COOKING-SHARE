package com.homecookingshare.command.recipe.service;

import com.homecookingshare.command.recipe.model.RecipeCommand;
import com.homecookingshare.command.recipe.model.RecipeCommand.AddMakeProcess;
import com.homecookingshare.command.recipe.model.RecipeCommand.ChangeLevel;
import com.homecookingshare.command.recipe.model.RecipeCommand.ChangeTitle;
import com.homecookingshare.command.recipe.model.RecipeCommand.RemoveMakeProcess;
import com.homecookingshare.common.Validator;
import com.homecookingshare.common.fileUpload.FileUploader;
import com.homecookingshare.domain.recipe.Cooker;
import com.homecookingshare.domain.recipe.Recipe;
import com.homecookingshare.domain.recipe.RecipeId;

public interface RecipeService {
	Recipe create(
			Validator<RecipeCommand.RegisterRecipe> validator,
			FileUploader fileUploader,
			RecipeCommand.RegisterRecipe command,
			Cooker cooker
		);

	void changeTitle(
			Validator<RecipeCommand.ChangeTitle> validator, 
			RecipeId targetRecipeId, 
			ChangeTitle command,
			Cooker recipeOwner
		);

	void addMakeProcess(
			Validator<RecipeCommand.AddMakeProcess> validator, 
			FileUploader fileUploader,
			RecipeId targetRecipeId, 
			AddMakeProcess command, 
			Cooker recipeOwner
		);

	void removedMakeProcess(
			Validator<RecipeCommand.RemoveMakeProcess> validator, 
			RecipeId targetRecipeId, 
			RemoveMakeProcess command, 
			Cooker recipeOwner
		);

	void changeLevel(
			Validator<RecipeCommand.ChangeLevel> validator, 
			RecipeId targetRecipeId, 
			ChangeLevel command, 
			Cooker recipeOwner
		);
}
