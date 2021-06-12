package com.homecookingshare.domain.member;

import com.homecookingshare.domain.member.event.AbstractMemberEvent;
import com.homecookingshare.domain.recipe.read.Recipe;

import lombok.Getter;

@Getter
public class UnInterestedRecipe extends AbstractMemberEvent{
	private final Recipe targetRecipe;
	public UnInterestedRecipe(Email who, Recipe targetRecipe) {
		this.targetUserEmail = who;
		this.targetRecipe = targetRecipe;
	}
}
