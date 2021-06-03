package com.homecookingshare.command.recipe.infra.validator;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.homecookingshare.command.recipe.exception.InvalidRecipeException;
import com.homecookingshare.command.recipe.model.RecipeCommand.AddMakeProcess;
import com.homecookingshare.command.recipe.model.RecipeCommand.AddMaterial;
import com.homecookingshare.command.recipe.model.RecipeCommand.RegisterRecipe;
import com.homecookingshare.domain.recipe.Recipe.Level;
import com.homecookingshare.domain.recipe.Recipe.RecipeCategory;
import com.homecookingshare.domain.recipe.Recipe.Serving;

@Component
public class RegisterRecipeValidator extends AbstractRecipeValidator<RegisterRecipe> {

	@Override
	public void validate(RegisterRecipe obj) {
		String title = obj.getTitle();
		List<AddMakeProcess> makeProcesses = obj.getMakeProcesses();
		List<AddMaterial> materials = obj.getMaterials();
		long makingTime = obj.getMakingTime();
		MultipartFile mainImage = obj.getMainImage();
		RecipeCategory category = obj.getCategory();
		Level level = obj.getLevel();
		Serving serving = obj.getServing();

		verifyNotNullObject(makeProcesses, new InvalidRecipeException("레시피 제작 과정을 입력해주세요."));
		verifyNotNullObject(materials, new InvalidRecipeException("레시피 재료를 입력해주세요."));

		int makeProcessSize = makeProcesses.size();
		if (makeProcessSize == 0) {
			throw new InvalidRecipeException("레시피 제작 과정을 입력해주세요.");
		}

		if (materials.size() == 0) {
			throw new InvalidRecipeException("레시피 재료를 입력해주세요.");
		}

		titleValidation(title);
		makingTimeValidation(makingTime);
		categoryValidation(category);
		levelValidation(level);
		servingValidation(serving);
		mainImageValidation(mainImage);
		materials.forEach(this::materialValidation);
		makeProcesses.forEach(this::makeProcessValidation);

		makeProcesses.sort((a, b) -> a.getProcessOrder() + b.getProcessOrder());

		for (int i = 0; i < makeProcessSize; i++) {
			if(makeProcesses.get(i).getProcessOrder() != i) {
				throw new InvalidRecipeException("레시피 제작 순서를 다시 입력해주세요.");
			}
		}
	}

}
