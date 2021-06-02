package com.homecookingshare.domain.recipe.read;

import java.util.Date;

import org.springframework.data.domain.AbstractAggregateRoot;

import com.homecookingshare.domain.recipe.Cooker;
import com.homecookingshare.domain.recipe.MakeProcesses;
import com.homecookingshare.domain.recipe.MakingTime;
import com.homecookingshare.domain.recipe.Materials;
import com.homecookingshare.domain.recipe.Recipe.Level;
import com.homecookingshare.domain.recipe.Recipe.RecipeCategory;
import com.homecookingshare.domain.recipe.Recipe.Serving;
import com.homecookingshare.domain.recipe.RecipeId;
import com.homecookingshare.domain.recipe.RecipeTitle;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recipe extends AbstractAggregateRoot<Recipe> {
	private RecipeId id;
	
	private Cooker cooker;

	private RecipeTitle title;
	private String mainImage;
	
	private RecipeCategory category;
	private Serving serving;
	private Level level;
	
	private MakingTime time;
	private Materials materials;
	private MakeProcesses makeProcess;
	
	private Date createDateTime;

	public void changeTitle(RecipeTitle title) {
		this.title = title;
	}

	public void changeMakeProcess(MakeProcesses makeProcesses) {
		this.makeProcess = makeProcesses;
	}

	public void changeLevel(Level level) {
		this.level = level;
	}
}
