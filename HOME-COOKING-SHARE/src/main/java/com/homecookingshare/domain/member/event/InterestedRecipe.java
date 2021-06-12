package com.homecookingshare.domain.member.event;

import com.homecookingshare.domain.member.Email;
import com.homecookingshare.domain.recipe.read.Recipe;

import lombok.Getter;

@Getter
public class InterestedRecipe extends AbstractMemberEvent{
	private final Recipe targetRecipe;
	
	public InterestedRecipe(Email who, Recipe targetRecipe) {
		this.targetUserEmail = who;
		this.targetRecipe = targetRecipe;
	}
}
