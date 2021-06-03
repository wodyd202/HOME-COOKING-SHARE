package com.homecookingshare.query.recipe.projector;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.homecookingshare.domain.recipe.ChangedTitle;
import com.homecookingshare.domain.recipe.event.AddedMakeProcess;
import com.homecookingshare.domain.recipe.event.AddedMaterial;
import com.homecookingshare.domain.recipe.event.ChangedLevel;
import com.homecookingshare.domain.recipe.event.ChangedMainImage;
import com.homecookingshare.domain.recipe.event.ChangedServing;
import com.homecookingshare.domain.recipe.event.RegisteredRecipe;
import com.homecookingshare.domain.recipe.event.RemovedMakeProcess;
import com.homecookingshare.domain.recipe.event.RemovedMaterial;
import com.homecookingshare.domain.recipe.read.Recipe;
import com.homecookingshare.query.recipe.infra.RecipeReadRepository;

import lombok.AllArgsConstructor;

@Async
@Component
@Transactional
@AllArgsConstructor
public class SimpleRecipeProjector implements RecipeProjector{
	private RecipeReadRepository recipeRepository;
	
	@Override
	@EventListener
	public void on(final RegisteredRecipe event) {
		Recipe recipe = Recipe
				.builder()
				.id(event.getTargetId())
				.cooker(event.getCooker())
				.title(event.getTitle())
				.mainImage(event.getMainImage())
				.category(event.getCategory())
				.serving(event.getServing())
				.level(event.getLevel())
				.time(event.getTime())
				.materials(event.getMaterials())
				.makeProcess(event.getMakeProcess())
				.build();
		recipeRepository.save(recipe);
	}

	@Override
	@EventListener
	public void on(final ChangedTitle event) {
		Recipe recipe = recipeRepository.findByRecipeId(event.getTargetId()).get();
		recipe.changeTitle(event.getTitle());
		recipeRepository.save(recipe);
	}

	@Override
	@EventListener
	public void on(final AddedMakeProcess event) {
		Recipe recipe = recipeRepository.findByRecipeId(event.getTargetId()).get();
		recipe.changeMakeProcesses(event.getMakeProcesses());
		recipeRepository.save(recipe);
	}

	@Override
	@EventListener
	public void on(final RemovedMakeProcess event) {
		Recipe recipe = recipeRepository.findByRecipeId(event.getTargetId()).get();
		recipe.changeMakeProcesses(event.getMakeProcesses());
		recipeRepository.save(recipe);
	}

	@Override
	@EventListener
	public void on(final ChangedLevel event) {
		Recipe recipe = recipeRepository.findByRecipeId(event.getTargetId()).get();
		recipe.changeLevel(event.getLevel());
		recipeRepository.save(recipe);
	}
	
	@Override
	@EventListener
	public void on(final ChangedMainImage event) {
		Recipe recipe = recipeRepository.findByRecipeId(event.getTargetId()).get();
		recipe.changeMainImage(event.getMainImage());
		recipeRepository.save(recipe);
	}

	@Override
	@EventListener
	public void on(final AddedMaterial event) {
		Recipe recipe = recipeRepository.findByRecipeId(event.getTargetId()).get();
		recipe.changeMaterials(event.getMaterials());
		recipeRepository.save(recipe);
	}

	@Override
	@EventListener
	public void on(final RemovedMaterial event) {
		Recipe recipe = recipeRepository.findByRecipeId(event.getTargetId()).get();
		recipe.changeMaterials(event.getMaterials());
		recipeRepository.save(recipe);
	}

	@Override
	@EventListener
	public void on(final ChangedServing event) {
		Recipe recipe = recipeRepository.findByRecipeId(event.getTargetId()).get();
		recipe.changeServing(event.getServing());
		recipeRepository.save(recipe);
	}

}
