package com.homecookingshare.member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.homecookingshare.domain.member.InterestRecipes;
import com.homecookingshare.domain.recipe.RecipeId;
import com.homecookingshare.domain.recipe.read.Recipe;

public class MemberInterestTest {
	
	Recipe mockRecipe = mock(Recipe.class);
	InterestRecipes interestRecipes = new InterestRecipes();

	@Test
	void unInterest() {
		interestRecipes.interest(mockRecipe);
		interestRecipes.unInterest(mockRecipe);
		assertThat(interestRecipes.getCount()).isEqualTo(0);
	}
	
	@Test
	void interest() {
		interestRecipes.interest(mockRecipe);
		assertThat(interestRecipes.getCount()).isEqualTo(1);
	}
	
	@Test
	void alreadyInterestRecipe() {
		interestRecipes.interest(mockRecipe);
		assertThat(interestRecipes.alreadyInterestRecipe(mockRecipe)).isTrue();
	}
	
	@BeforeEach
	void setUp() {
		when(mockRecipe.getId())
			.thenReturn(new RecipeId("recipeid"));
	}
}
