package com.homecookingshare;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.homecookingshare.query.recipe.infra.RecipeReadRepository;

@SpringBootTest
public class LoadTest {
	
	@Autowired
	private RecipeReadRepository recipeReadRepository;
	
	@Test
	void load() {
	}
}
