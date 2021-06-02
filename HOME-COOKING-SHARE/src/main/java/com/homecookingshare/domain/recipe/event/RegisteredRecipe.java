package com.homecookingshare.domain.recipe.event;

import java.util.Date;

import com.homecookingshare.domain.recipe.Cooker;
import com.homecookingshare.domain.recipe.MakeProcesses;
import com.homecookingshare.domain.recipe.MakingTime;
import com.homecookingshare.domain.recipe.Materials;
import com.homecookingshare.domain.recipe.Recipe.Level;
import com.homecookingshare.domain.recipe.Recipe.RecipeCategory;
import com.homecookingshare.domain.recipe.Recipe.Serving;
import com.homecookingshare.domain.recipe.RecipeId;
import com.homecookingshare.domain.recipe.RecipeTitle;

import lombok.Getter;

@Getter
public class RegisteredRecipe extends AbstractRecipeEvent{
	private final Cooker cooker;
	private final RecipeTitle title;
	private final String mainImage;
	private final RecipeCategory category;
	private final Serving serving;
	private final Level level;
	private final MakingTime time;
	private final Materials materials;
	private final MakeProcesses makeProcess;
	private final Date createDateTime;
	
	public RegisteredRecipe(
			RecipeId id,
			Cooker cooker,
			RecipeTitle title,
			String mainImage,
			RecipeCategory category,
			Serving serving,
			Level level,
			MakingTime time,
			Materials materials,
			MakeProcesses makeProcess,
			Date createDateTime
		) {
		this.targetId = id;
		this.cooker = cooker;
		this.title = title;
		this.mainImage = mainImage;
		this.category = category;
		this.serving = serving;
		this.level = level;
		this.time = time;
		this.materials = materials;
		this.makeProcess = makeProcess;
		this.createDateTime = createDateTime;
	}
}
