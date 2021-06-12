package com.homecookingshare.domain.member;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.JoinColumn;

import com.homecookingshare.domain.recipe.RecipeId;
import com.homecookingshare.domain.recipe.read.Recipe;

import lombok.Getter;

@Getter
public class InterestRecipes implements Serializable {
	private static final long serialVersionUID = 1L;

	@ElementCollection
	@CollectionTable(name = "MEMBER_RECIPE_INTEREST", joinColumns = @JoinColumn(name = "member_email", referencedColumnName = "email"))
	private Set<RecipeId> interestRecipes;

	public InterestRecipes() {
		this.interestRecipes = new HashSet<>();
	}

	public boolean alreadyInterestRecipe(Recipe targetRecipe) {
		return interestRecipes.contains(targetRecipe.getId());
	}
	
	public int getCount() {
		return this.interestRecipes.size();
	}
	
	public void interest(Recipe targetRecipe) {
		interestRecipes.add(targetRecipe.getId());
	}

	public void unInterest(Recipe targetRecipe) {
		interestRecipes.remove(targetRecipe.getId());
	}
	
}
