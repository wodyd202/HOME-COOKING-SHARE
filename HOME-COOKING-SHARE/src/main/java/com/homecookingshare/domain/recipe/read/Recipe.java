package com.homecookingshare.domain.recipe.read;

import java.util.Date;

import org.springframework.data.domain.AbstractAggregateRoot;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.homecookingshare.domain.recipe.Cooker;
import com.homecookingshare.domain.recipe.MakeProcesses;
import com.homecookingshare.domain.recipe.MakingTime;
import com.homecookingshare.domain.recipe.Materials;
import com.homecookingshare.domain.recipe.Recipe.Level;
import com.homecookingshare.domain.recipe.Recipe.RecipeCategory;
import com.homecookingshare.domain.recipe.Recipe.Serving;
import com.homecookingshare.domain.recipe.RecipeId;
import com.homecookingshare.domain.recipe.RecipeMainImage;
import com.homecookingshare.domain.recipe.RecipeTitle;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class Recipe extends AbstractAggregateRoot<Recipe> {
	private RecipeId id;
	
	private Cooker cooker;

	private RecipeTitle title;
	private RecipeMainImage mainImage;
	
	private RecipeCategory category;
	private Serving serving;
	private Level level;
	
	private MakingTime time;
	private Materials materials;
	private MakeProcesses makeProcess;
	
	private Date createDateTime;

	public Recipe(RecipeId recipeId) {
		this.id = recipeId;
	}
	
	public void changeTitle(RecipeTitle title) {
		this.title = title;
	}

	public void changeMakeProcesses(MakeProcesses makeProcesses) {
		this.makeProcess = makeProcesses;
	}

	public void changeLevel(Level level) {
		this.level = level;
	}

	public void changeMainImage(RecipeMainImage mainImage) {
		this.mainImage = mainImage;
	}

	public void changeMaterials(Materials materials) {
		this.materials = materials;
	}

	public void changeServing(Serving serving) {
		this.serving = serving;
	}
}
