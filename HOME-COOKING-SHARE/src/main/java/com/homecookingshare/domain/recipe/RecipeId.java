package com.homecookingshare.domain.recipe;

import java.io.Serializable;
import java.util.UUID;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "recipeId")
public class RecipeId implements Serializable{
	private static final long serialVersionUID = 1L;
	private String recipeId;
	
	public static RecipeId create() {
		return new RecipeId(UUID.randomUUID().toString());
	}
	
	@Override
	public String toString() {
		return this.recipeId;
	}
}
