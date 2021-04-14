package com.homecookingshare.recipe;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import com.homecookingshare.common.Validator;
import com.homecookingshare.member.Email;
import com.homecookingshare.member.MemberTest;
import com.homecookingshare.recipe.Recipe.RecipeLevel;
import com.homecookingshare.recipe.Recipe.Serving;

public class RecipeRegisterServiceTest extends MemberTest{
	
	@Test
	void test() {
		RegisterRecipe registerRecipe = RegisterRecipe
				.builder()
				.writer("wodyd202@naver.com")
				.title("레시피명")
				.mainImg(new MockMultipartFile("file.png", new byte[] {}))
				.content("레시피 소개글")
				.level(RecipeLevel.LEVEL_1)
				.serving(Serving.SERVING_3)
				.time(300000000)
				.category("밑반찬")
				.tip("")
				.material(Arrays.asList(new RegisterMaterial("깐마늘","3스푼")).stream().collect(Collectors.toSet()))
				.build();
		Validator<RegisterRecipe> registerRecipeValidator = new RegisterRecipeValidator();
		RecipeRepository recipeRepository = new FakeRecipeRepository();
		RecipeRegisterService recipeRegisterService = new RecipeRegisterService(registerRecipeValidator,recipeRepository,memberRepository);
		recipeRegisterService.register(registerRecipe);
		List<RecipeListData> recipes = recipeRepository.findAll(RecipeSearchDTO.builder().writer(new Email("wodyd202@naver.com")).build());
		assertThat(recipes.size()).isEqualTo(1);
	}
}
