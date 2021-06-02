package com.homecookingshare.recipe;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import com.homecookingshare.command.recipe.exception.InvalidRecipeException;
import com.homecookingshare.command.recipe.infra.validator.RegisterRecipeValidator;
import com.homecookingshare.command.recipe.model.RecipeCommand;
import com.homecookingshare.common.Validator;
import com.homecookingshare.domain.recipe.Recipe.Level;
import com.homecookingshare.domain.recipe.Recipe.RecipeCategory;
import com.homecookingshare.domain.recipe.Recipe.Serving;

public class RecipeValidatorTest {
	
	Validator<RecipeCommand.RegisterRecipe> validator = new RegisterRecipeValidator();

	@Test
	void 제작_과정_누락() {
		RecipeCommand.AddMaterial addedMaterial_1 = new RecipeCommand.AddMaterial(
				"재료명","용량"
				);
		RecipeCommand.AddMaterial addedMaterial_2 = new RecipeCommand.AddMaterial(
				"재료명","용량"
				);
		RecipeCommand.RegisterRecipe command = new RecipeCommand.RegisterRecipe(
					"타이틀",
					0,
					RecipeCategory.Japanese,
					Level.FOUR,
					Serving.FIVE,
					60000,
					Arrays.asList(addedMaterial_1,addedMaterial_2),
					Arrays.asList()
				);
		assertThrows(InvalidRecipeException.class, ()->{
			validator.validate(command);
		});
	}
	
	@Test
	void 재료_누락() {
		RecipeCommand.AddMakeProcess addedMakeProcess_1 = new RecipeCommand.AddMakeProcess(
				new MockMultipartFile("이름.jpg","이름.jpg","" ,new byte [] {}),
				"내용",
				0
				);
		RecipeCommand.AddMakeProcess addedMakeProcess_2 = new RecipeCommand.AddMakeProcess(
				new MockMultipartFile("이름.jpg","이름.jpg","" ,new byte [] {}),
				"내용",
				1
				);
		RecipeCommand.AddMakeProcess addedMakeProcess_3 = new RecipeCommand.AddMakeProcess(
				new MockMultipartFile("이름.jpg","이름.jpg","" ,new byte [] {}),
				"내용",
				2
				);
		RecipeCommand.RegisterRecipe command = new RecipeCommand.RegisterRecipe(
					"타이틀",
					0,
					RecipeCategory.Chinese,
					Level.FOUR,
					Serving.FIVE,
					60000,
					Arrays.asList(),
					Arrays.asList(addedMakeProcess_1,addedMakeProcess_2,addedMakeProcess_3)
				);
		assertThrows(InvalidRecipeException.class, ()->{
			validator.validate(command);
		});
	}
	
	@Test
	void 정상_입력_케이스() {
		RecipeCommand.AddMaterial addedMaterial_1 = new RecipeCommand.AddMaterial(
				"재료명","용량"
				);
		RecipeCommand.AddMaterial addedMaterial_2 = new RecipeCommand.AddMaterial(
				"재료명","용량"
				);
		RecipeCommand.AddMakeProcess addedMakeProcess_1 = new RecipeCommand.AddMakeProcess(
				new MockMultipartFile("이름.jpg","이름.jpg","" ,new byte [] {}),
				"내용",
				0
				);
		RecipeCommand.AddMakeProcess addedMakeProcess_2 = new RecipeCommand.AddMakeProcess(
				new MockMultipartFile("이름.jpg","이름.jpg","" ,new byte [] {}),
				"내용",
				1
				);
		RecipeCommand.AddMakeProcess addedMakeProcess_3 = new RecipeCommand.AddMakeProcess(
				new MockMultipartFile("이름.jpg","이름.jpg","" ,new byte [] {}),
				"내용",
				2
				);
		RecipeCommand.RegisterRecipe command = new RecipeCommand.RegisterRecipe(
					"타이틀",
					0,
					RecipeCategory.Korean,
					Level.FOUR,
					Serving.FIVE,
					60000,
					Arrays.asList(addedMaterial_1,addedMaterial_2),
					Arrays.asList(addedMakeProcess_1,addedMakeProcess_2,addedMakeProcess_3)
				);
		validator.validate(command);
	}
	
}
