package com.homecookingshare.query.recipe.projector;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.homecookingshare.common.Executable;
import com.homecookingshare.domain.recipe.ChangedTitle;
import com.homecookingshare.domain.recipe.event.AddedMakeProcess;
import com.homecookingshare.domain.recipe.event.ChangedLevel;
import com.homecookingshare.domain.recipe.event.RegisteredRecipe;
import com.homecookingshare.domain.recipe.event.RemovedMakeProcess;
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
	public void on(RegisteredRecipe event) {
		execute(()->{
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
			return recipe;
		});
	}

	@Override
	@EventListener
	public void on(ChangedTitle event) {
		execute(()->{
			Recipe recipe = recipeRepository.findByRecipeId(event.getTargetId()).get();
			recipe.changeTitle(event.getTitle());
			return recipe;
		});
	}

	@Override
	@EventListener
	public void on(AddedMakeProcess event) {
		execute(()->{
			Recipe recipe = recipeRepository.findByRecipeId(event.getTargetId()).get();
			recipe.changeMakeProcess(event.getMakeProcesses());
			return recipe;
		});
	}

	@Override
	@EventListener
	public void on(RemovedMakeProcess event) {
		execute(()->{
			Recipe recipe = recipeRepository.findByRecipeId(event.getTargetId()).get();
			recipe.changeMakeProcess(event.getMakeProcesses());
			return recipe;
		});
	}

	@Override
	@EventListener
	public void on(ChangedLevel event) {
		execute(()->{
			Recipe recipe = recipeRepository.findByRecipeId(event.getTargetId()).get();
			recipe.changeLevel(event.getLevel());
			return recipe;
		});
	}
	
	private void execute(Executable<?> executable) {
		recipeRepository.save((Recipe) executable.execute());
	}

}
