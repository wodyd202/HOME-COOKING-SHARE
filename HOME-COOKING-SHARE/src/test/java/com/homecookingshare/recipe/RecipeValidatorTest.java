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
		RecipeCommand.RegisterRecipe command = new RecipeCommand.RegisterRecipe(
					"타이틀",
					new MockMultipartFile("file.jpg", "file.jpg","",new byte[] {}),
					RecipeCategory.Japanese,
					Level.FOUR,
					Serving.FIVE,
					60000,
					Arrays.asList("재료명"),
					Arrays.asList("용량"),
					Arrays.asList(),
					Arrays.asList(),
					Arrays.asList()
				);
		assertThrows(InvalidRecipeException.class, ()->{
			validator.validate(command);
		});
	}
	
	@Test
	void 재료_누락() {
		RecipeCommand.RegisterRecipe command = new RecipeCommand.RegisterRecipe(
					"타이틀",
					new MockMultipartFile("file.jpg", "file.jpg","",new byte[] {}),
					RecipeCategory.Chinese,
					Level.FOUR,
					Serving.FIVE,
					60000,
					Arrays.asList(),
					Arrays.asList(),
					Arrays.asList(new MockMultipartFile("이름.jpg","이름.jpg","" ,new byte [] {})),
					Arrays.asList("내용"),
					Arrays.asList(1)
				);
		assertThrows(InvalidRecipeException.class, ()->{
			validator.validate(command);
		});
	}
	
	@Test
	void 정상_입력_케이스() {
		RecipeCommand.RegisterRecipe command = new RecipeCommand.RegisterRecipe(
					"타이틀",
					new MockMultipartFile("file.jpg", "file.jpg","",new byte[] {}),
					RecipeCategory.Korean,
					Level.FOUR,
					Serving.FIVE,
					60000,
					Arrays.asList("재료명"),
					Arrays.asList("용량"),
					Arrays.asList(new MockMultipartFile("이름.jpg","이름.jpg","" ,new byte [] {})),
					Arrays.asList("내용"),
					Arrays.asList(0)
				);
		validator.validate(command);
	}
	
}
