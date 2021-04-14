package com.homecookingshare.recipe;

import com.homecookingshare.common.AbstractRegisterService;
import com.homecookingshare.common.Validator;
import com.homecookingshare.member.infrastructure.MemberRepository;

public class RecipeRegisterService extends AbstractRegisterService<RegisterRecipe> {
	private final RecipeRepository recipeRepository;
	private final MemberRepository memberRepository;
	
	@Override
	protected void save(RegisterRecipe obj) {
		recipeRepository.save(obj.toEntity(Recipe.createId()));
	}
	
	public RecipeRegisterService(Validator<RegisterRecipe> validate, RecipeRepository recipeRepository, MemberRepository memberRepository) {
		super(validate);
		this.memberRepository = memberRepository;
		this.recipeRepository = recipeRepository;
	}

}
