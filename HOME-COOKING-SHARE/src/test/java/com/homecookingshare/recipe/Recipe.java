package com.homecookingshare.recipe;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

import com.homecookingshare.member.Email;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recipe {

	private RecipeId id;
	private RecipeTitle title;
	private RecipeDetail detail;
	private RecipeCategory category;
	
	private Email writer;
	private Date createDate;
	
	public static RecipeId createId() {
		return new RecipeId(UUID.randomUUID().toString());
	}

	public Recipe(RecipeId createId, RegisterRecipe registerRecipe) {
		this.id = createId;
		this.title = new RecipeTitle(registerRecipe.getTitle());
		this.category = new RecipeCategory(registerRecipe.getCategory());
		this.writer = new Email(registerRecipe.getWriter());
		this.createDate = new Date();

		this.detail = RecipeDetail.builder().content(registerRecipe.getContent()).level(registerRecipe.getLevel())
				.serving(registerRecipe.getServing()).time(registerRecipe.getTime()).tip(registerRecipe.getTip())
				.marterials(registerRecipe.getMaterial().stream()
						.map(c -> new RecipeMarterial(c.getMaterial(), c.getCapacity())).collect(Collectors.toSet()))
				.build();
	}

	public enum Serving {
		SERVING_1, SERVING_2, SERVING_3, SERVING_4, SERVING_5,
	}

	public enum RecipeLevel {
		LEVEL_1, LEVEL_2, LEVEL_3, LEVEL_4, LEVEL_5
	}
}
