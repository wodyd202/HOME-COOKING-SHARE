package com.homecookingshare.command.recipe.infra.validator;

import org.springframework.stereotype.Component;

import com.homecookingshare.command.recipe.exception.InvalidRecipeException;
import com.homecookingshare.command.recipe.model.RecipeCommand.RemoveMakeProcess;

@Component
public class RemoveMakeProcessValidator extends AbstractRecipeValidator<RemoveMakeProcess>{

	@Override
	public void validate(RemoveMakeProcess obj) {
		if(obj.getProcessOrder() < 0) {
			throw new InvalidRecipeException("삭제하고자 하는 레시피 순서가 잘못입력되었습니다.");
		}
	}

}
