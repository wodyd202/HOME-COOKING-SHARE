package com.homecookingshare.recipe;

import java.util.Set;

import com.homecookingshare.recipe.Recipe.RecipeLevel;
import com.homecookingshare.recipe.Recipe.Serving;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecipeDetail {
	private String content;
	private RecipeLevel level;
	private Serving serving;
	private long time;
	private String tip;
	private Set<RecipeMarterial> marterials;
}
