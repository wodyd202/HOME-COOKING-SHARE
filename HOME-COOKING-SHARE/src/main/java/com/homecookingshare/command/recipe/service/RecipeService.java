package com.homecookingshare.command.recipe.service;

import com.homecookingshare.command.recipe.model.RecipeCommand;
import com.homecookingshare.command.recipe.model.RecipeCommand.AddMakeProcess;
import com.homecookingshare.command.recipe.model.RecipeCommand.AddMaterial;
import com.homecookingshare.command.recipe.model.RecipeCommand.ChangeLevel;
import com.homecookingshare.command.recipe.model.RecipeCommand.ChangeMainImage;
import com.homecookingshare.command.recipe.model.RecipeCommand.ChangeServing;
import com.homecookingshare.command.recipe.model.RecipeCommand.ChangeTitle;
import com.homecookingshare.command.recipe.model.RecipeCommand.RemoveMakeProcess;
import com.homecookingshare.command.recipe.model.RecipeCommand.RemoveMaterial;
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

	Recipe changeTitle(
			Validator<RecipeCommand.ChangeTitle> validator, 
			RecipeId targetRecipeId, 
			ChangeTitle command,
			Cooker recipeOwner
		);

	Recipe addMakeProcess(
			Validator<RecipeCommand.AddMakeProcess> validator, 
			FileUploader fileUploader,
			RecipeId targetRecipeId, 
			AddMakeProcess command, 
			Cooker recipeOwner
		);

	Recipe removeMakeProcess(
			Validator<RecipeCommand.RemoveMakeProcess> validator, 
			RecipeId targetRecipeId, 
			RemoveMakeProcess command, 
			Cooker recipeOwner
		);

	Recipe changeLevel(
			Validator<RecipeCommand.ChangeLevel> validator, 
			RecipeId targetRecipeId, 
			ChangeLevel command, 
			Cooker recipeOwner
		);

	Recipe changeMainImage(
			Validator<ChangeMainImage> validator,
			FileUploader fileUploader,
			RecipeId targetRecipeId, 
			ChangeMainImage command,
			Cooker recipeOwner
		);

	Recipe addMaterial(
			Validator<AddMaterial> validator, 
			RecipeId targetRecipeId, 
			AddMaterial command, 
			Cooker recipeOwner
		);

	Recipe removeMaterial(
			Validator<RemoveMaterial> validator, 
			RecipeId targetRecipeId, 
			RemoveMaterial command,
			Cooker recipeOwner
		);

	Recipe changeServing(
			Validator<ChangeServing> validator, 
			RecipeId targetRecipeId, 
			ChangeServing command,
			Cooker recipeOwner
		);
}
