package com.homecookingshare.recipe;

import com.homecookingshare.common.AbstractRegisterService;
import com.homecookingshare.common.Validator;
import com.homecookingshare.member.infrastructure.MemberEventRepository;

public class RecipeRegisterService extends AbstractRegisterService<RegisterRecipe> {
	private final RecipeRepository recipeRepository;
	private final MemberEventRepository memberRepository;
	
	@Override
	protected void save(RegisterRecipe obj) {
		recipeRepository.save(obj.toEntity(Recipe.createId()));
	}
	
	public RecipeRegisterService(Validator<RegisterRecipe> validate, RecipeRepository recipeRepository, MemberEventRepository memberRepository) {
		super(validate);
		this.memberRepository = memberRepository;
		this.recipeRepository = recipeRepository;
	}

}
