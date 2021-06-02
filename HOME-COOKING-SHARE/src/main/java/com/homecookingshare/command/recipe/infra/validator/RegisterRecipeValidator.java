package com.homecookingshare.command.recipe.infra.validator;

import java.util.List;

import org.springframework.stereotype.Component;

import com.homecookingshare.command.recipe.exception.InvalidRecipeException;
import com.homecookingshare.command.recipe.model.RecipeCommand.AddMakeProcess;
import com.homecookingshare.command.recipe.model.RecipeCommand.AddMaterial;
import com.homecookingshare.command.recipe.model.RecipeCommand.RegisterRecipe;

@Component
public class RegisterRecipeValidator extends AbstractRecipeValidator<RegisterRecipe> {

	@Override
	public void validate(RegisterRecipe obj) {
		String title = obj.getTitle();
		List<AddMakeProcess> makeProcesses = obj.getMakeProcesses();
		List<AddMaterial> materials = obj.getMaterials();
		long makingTime = obj.getMakingTime();
		int mainImageIdx = obj.getMainImageIdx();
		
		verifyNotNullObject(makeProcesses, new InvalidRecipeException("레시피 제작 과정을 입력해주세요."));
		verifyNotNullObject(materials, new InvalidRecipeException("레시피 재료를 입력해주세요."));

		if(makeProcesses.size() == 0) {
			throw new InvalidRecipeException("레시피 제작 과정을 입력해주세요.");
		}
		
		if(materials.size() == 0) {
			throw new InvalidRecipeException("레시피 재료를 입력해주세요.");
		}
		
		titleValidation(title);
		makingTimeValidation(makingTime);
		materials.forEach(this::materialValidation);
		makeProcesses.forEach(this::makeProcessValidation);
		mainImageIdxValidation(mainImageIdx, makeProcesses);
	}
	
	private void mainImageIdxValidation(long mainImageIdx, List<AddMakeProcess> makeProcesses) {
		if(mainImageIdx < 0 || mainImageIdx >= makeProcesses.size()) {
			throw new InvalidRecipeException("메인 이미지를 다시 지정해주세요.");
		}
	}
	
}
