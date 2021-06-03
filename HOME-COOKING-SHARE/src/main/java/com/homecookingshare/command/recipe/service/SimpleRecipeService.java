package com.homecookingshare.command.recipe.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.homecookingshare.command.member.exception.MemberNotFoundException;
import com.homecookingshare.command.member.infra.JpaMemberRepository;
import com.homecookingshare.command.recipe.exception.InvalidRecipeException;
import com.homecookingshare.command.recipe.exception.RecipeNotFoundException;
import com.homecookingshare.command.recipe.infra.JpaRecipeRepository;
import com.homecookingshare.command.recipe.model.RecipeCommand.AddMakeProcess;
import com.homecookingshare.command.recipe.model.RecipeCommand.AddMaterial;
import com.homecookingshare.command.recipe.model.RecipeCommand.ChangeLevel;
import com.homecookingshare.command.recipe.model.RecipeCommand.ChangeMainImage;
import com.homecookingshare.command.recipe.model.RecipeCommand.ChangeServing;
import com.homecookingshare.command.recipe.model.RecipeCommand.ChangeTitle;
import com.homecookingshare.command.recipe.model.RecipeCommand.RegisterRecipe;
import com.homecookingshare.command.recipe.model.RecipeCommand.RemoveMakeProcess;
import com.homecookingshare.command.recipe.model.RecipeCommand.RemoveMaterial;
import com.homecookingshare.common.Validator;
import com.homecookingshare.common.fileUpload.FileUploader;
import com.homecookingshare.domain.member.Email;
import com.homecookingshare.domain.recipe.Cooker;
import com.homecookingshare.domain.recipe.MakeProcess;
import com.homecookingshare.domain.recipe.Recipe;
import com.homecookingshare.domain.recipe.RecipeId;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SimpleRecipeService implements RecipeService {
	private JpaRecipeRepository jpaRecipeRepository;
	private JpaMemberRepository jpaMemberRepsitory;

	@Override
	public Recipe create(
			Validator<RegisterRecipe> validator, 
			FileUploader fileUploader, 
			RegisterRecipe command, 
			Cooker cooker
		) {
		validator.validate(command);
		jpaMemberRepsitory.findById(new Email(cooker.getEmail()))
			.orElseThrow(()->new MemberNotFoundException("해당 회원이 존재하지 않습니다."));
		Recipe recipe = Recipe.create(command, cooker);
		
		uploadRecipeImages(fileUploader, command, recipe);
		jpaRecipeRepository.save(recipe);
		return recipe;
	}

	private void uploadRecipeImages(FileUploader fileUploader, RegisterRecipe command, Recipe recipe) {
		List<MakeProcess> realMakeProcesses = recipe.getMakeProcess().getMakeProcess();
		List<AddMakeProcess> makeProcesses = command.getMakeProcesses();
		int makeProccessesCount = command.getMakeProcesses().size();

		fileUploader.uploadFile(command.getMainImage(), recipe.getMainImage().getPath());
		
		for(int i =0; i < makeProccessesCount; i++) {
			fileUploader.uploadFile(
					makeProcesses.get(i).getFile(), 
					realMakeProcesses.get(i).getImagePath()
				);
		}
	}

	@Override
	public Recipe changeTitle(
			Validator<ChangeTitle> validator, 
			RecipeId targetRecipeId, 
			ChangeTitle command,
			Cooker recipeOwner
		) {
		validator.validate(command);
		Recipe findRecipe = jpaRecipeRepository.findById(targetRecipeId)
				.orElseThrow(()->new RecipeNotFoundException("해당 레시피가 존재하지 않습니다."));
		if(!recipeOwner.isMyRecipe(findRecipe)) {
			throw new InvalidRecipeException("자신의 레시피만 수정할 수 있습니다.");
		}
		findRecipe.changeTitle(command.getTitle());
		jpaRecipeRepository.save(findRecipe);
		return findRecipe;
	}

	@Override
	public Recipe addMakeProcess(
			Validator<AddMakeProcess> validator, 
			FileUploader fileUploader,
			RecipeId targetRecipeId, 
			AddMakeProcess command,
			Cooker recipeOwner
		) {
		validator.validate(command);
		Recipe findRecipe = jpaRecipeRepository.findById(targetRecipeId)
				.orElseThrow(()->new RecipeNotFoundException("해당 레시피가 존재하지 않습니다."));
		if(!recipeOwner.isMyRecipe(findRecipe)) {
			throw new InvalidRecipeException("자신의 레시피만 수정할 수 있습니다.");
		}
		MakeProcess makeProcess = findRecipe.addMakeProcess(command);
		fileUploader.uploadFile(command.getFile(), makeProcess.getImagePath());
		jpaRecipeRepository.save(findRecipe);
		return findRecipe;
	}

	@Override
	public Recipe removeMakeProcess(
			Validator<RemoveMakeProcess> validator, 
			RecipeId targetRecipeId,
			RemoveMakeProcess command, 
			Cooker recipeOwner
		) {
		validator.validate(command);
		Recipe findRecipe = jpaRecipeRepository.findById(targetRecipeId)
				.orElseThrow(()->new RecipeNotFoundException("해당 레시피가 존재하지 않습니다."));
		if(!recipeOwner.isMyRecipe(findRecipe)) {
			throw new InvalidRecipeException("자신의 레시피만 수정할 수 있습니다.");
		}
		findRecipe.removeMakeProccess(command);
		jpaRecipeRepository.save(findRecipe);
		return findRecipe;
	}

	@Override
	public Recipe changeLevel(
			Validator<ChangeLevel> validator, 
			RecipeId targetRecipeId, 
			ChangeLevel command,
			Cooker recipeOwner
		) {
		validator.validate(command);
		Recipe findRecipe = jpaRecipeRepository.findById(targetRecipeId)
				.orElseThrow(()->new RecipeNotFoundException("해당 레시피가 존재하지 않습니다."));
		if(!recipeOwner.isMyRecipe(findRecipe)) {
			throw new InvalidRecipeException("자신의 레시피만 수정할 수 있습니다.");
		}
		findRecipe.changeLevel(command);
		jpaRecipeRepository.save(findRecipe);
		return findRecipe;
	}

	@Override
	public Recipe changeMainImage(
			Validator<ChangeMainImage> validator, 
			FileUploader fileUploader,
			RecipeId targetRecipeId, 
			ChangeMainImage command, 
			Cooker recipeOwner
		) {
		validator.validate(command);
		Recipe findRecipe = jpaRecipeRepository.findById(targetRecipeId)
				.orElseThrow(()->new RecipeNotFoundException("해당 레시피가 존재하지 않습니다."));
		if(!recipeOwner.isMyRecipe(findRecipe)) {
			throw new InvalidRecipeException("자신의 레시피만 수정할 수 있습니다.");
		}
		findRecipe.changeMainImage(command);
		fileUploader.uploadFile(command.getFile(), findRecipe.getMainImage().getPath());
		jpaRecipeRepository.save(findRecipe);
		return findRecipe;
	}

	@Override
	public Recipe addMaterial(
			Validator<AddMaterial> validator, 
			RecipeId targetRecipeId, 
			AddMaterial command,
			Cooker recipeOwner
		) {
		validator.validate(command);
		Recipe findRecipe = jpaRecipeRepository.findById(targetRecipeId)
				.orElseThrow(()->new RecipeNotFoundException("해당 레시피가 존재하지 않습니다."));
		if(!recipeOwner.isMyRecipe(findRecipe)) {
			throw new InvalidRecipeException("자신의 레시피만 수정할 수 있습니다.");
		}
		findRecipe.addMaterial(command);
		jpaRecipeRepository.save(findRecipe);
		return findRecipe;
	}

	@Override
	public Recipe removeMaterial(
			Validator<RemoveMaterial> validator, 
			RecipeId targetRecipeId, 
			RemoveMaterial command,
			Cooker recipeOwner
		) {
		validator.validate(command);
		Recipe findRecipe = jpaRecipeRepository.findById(targetRecipeId)
				.orElseThrow(()->new RecipeNotFoundException("해당 레시피가 존재하지 않습니다."));
		if(!recipeOwner.isMyRecipe(findRecipe)) {
			throw new InvalidRecipeException("자신의 레시피만 수정할 수 있습니다.");
		}
		findRecipe.removeMaterial(command);
		jpaRecipeRepository.save(findRecipe);
		return findRecipe;
	}

	@Override
	public Recipe changeServing(
			Validator<ChangeServing> validator, 
			RecipeId targetRecipeId, 
			ChangeServing command,
			Cooker recipeOwner
		) {
		validator.validate(command);
		Recipe findRecipe = jpaRecipeRepository.findById(targetRecipeId)
				.orElseThrow(()->new RecipeNotFoundException("해당 레시피가 존재하지 않습니다."));
		if(!recipeOwner.isMyRecipe(findRecipe)) {
			throw new InvalidRecipeException("자신의 레시피만 수정할 수 있습니다.");
		}
		findRecipe.changeServing(command);
		jpaRecipeRepository.save(findRecipe);
		return findRecipe;
	}
}
