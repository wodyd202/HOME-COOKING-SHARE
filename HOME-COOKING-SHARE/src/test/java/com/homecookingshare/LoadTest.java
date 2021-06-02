package com.homecookingshare;

import java.util.Arrays;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.homecookingshare.domain.recipe.Cooker;
import com.homecookingshare.domain.recipe.MakeProcess;
import com.homecookingshare.domain.recipe.MakeProcesses;
import com.homecookingshare.domain.recipe.MakingTime;
import com.homecookingshare.domain.recipe.Material;
import com.homecookingshare.domain.recipe.Materials;
import com.homecookingshare.domain.recipe.Recipe.Level;
import com.homecookingshare.domain.recipe.Recipe.RecipeCategory;
import com.homecookingshare.domain.recipe.Recipe.Serving;
import com.homecookingshare.domain.recipe.RecipeId;
import com.homecookingshare.domain.recipe.RecipeTitle;
import com.homecookingshare.domain.recipe.read.Recipe;
import com.homecookingshare.query.recipe.infra.RecipeReadRepository;

@SpringBootTest
public class LoadTest {
	
	@Autowired
	private RecipeReadRepository recipeReadRepository;
	
	@Test
	void load() {
		Recipe recipe = Recipe
				.builder()
				.id(RecipeId.create())
				.cooker(new Cooker("test@naver.com"))
				.title(new RecipeTitle("타이틀"))
				.mainImage("메인이미지")
				.category(RecipeCategory.Korean)
				.serving(Serving.ETC)
				.level(Level.FIVE)
				.time(new MakingTime(10000L))
				.materials(new Materials(Arrays.asList(new Material("이름","용량"))))
				.makeProcess(new MakeProcesses(Arrays.asList(new MakeProcess("sdf","fsd",1))))
				.createDateTime(new Date())
				.build();
		recipeReadRepository.save(recipe);
	}
}
