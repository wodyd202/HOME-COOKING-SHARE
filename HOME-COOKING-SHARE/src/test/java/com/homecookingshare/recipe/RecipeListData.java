package com.homecookingshare.recipe;

import com.homecookingshare.recipe.Recipe.RecipeLevel;
import com.homecookingshare.recipe.Recipe.Serving;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecipeListData {
	private String writer;
	private String title;
	private String mainImg;
	private String content;
	private RecipeLevel level;
	private Serving serving;
	private long time;
	private String category;
	private String tip;
}
