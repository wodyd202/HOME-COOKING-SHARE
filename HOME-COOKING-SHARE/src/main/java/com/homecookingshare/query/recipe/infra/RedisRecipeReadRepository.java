package com.homecookingshare.query.recipe.infra;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homecookingshare.domain.recipe.Cooker;
import com.homecookingshare.domain.recipe.MakeProcesses;
import com.homecookingshare.domain.recipe.MakingTime;
import com.homecookingshare.domain.recipe.Materials;
import com.homecookingshare.domain.recipe.Recipe.Level;
import com.homecookingshare.domain.recipe.Recipe.RecipeCategory;
import com.homecookingshare.domain.recipe.Recipe.Serving;
import com.homecookingshare.domain.recipe.RecipeId;
import com.homecookingshare.domain.recipe.RecipeMainImage;
import com.homecookingshare.domain.recipe.RecipeTitle;
import com.homecookingshare.domain.recipe.read.Recipe;
import com.homecookingshare.query.recipe.model.RecipeSearch;

@Repository
@SuppressWarnings({"rawtypes","unchecked"})
public class RedisRecipeReadRepository implements RecipeReadRepository{
	@Autowired
	private RedisTemplate<String, Object> template;
	
	@Value("${redis.recipe-list.key}")
	private String RECIPE_LIST_KEY;

	@Value("${redis.recipe.key}")
	private String RECIPE_KEY;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Override
	public void save(Recipe recipe) {
		RecipeId id = recipe.getId();
		HashOperations hashOperations = template.opsForHash();
		SetOperations setOperations = template.opsForSet();
		
		Optional<Recipe> findByRecipeId = findByRecipeId(id);
		if(findByRecipeId.isPresent()) {
			Recipe findRecipe = findByRecipeId.get();
			setOperations.remove(RECIPE_LIST_KEY + ":serving:" + findRecipe.getServing().toString(), id.getRecipeId());
			setOperations.remove(RECIPE_LIST_KEY + ":level:" + findRecipe.getLevel().toString(), id.getRecipeId());
			setOperations.remove(RECIPE_LIST_KEY + ":category:" + findRecipe.getCategory().toString(), id.getRecipeId());
		}
		
		setOperations.add(RECIPE_LIST_KEY, id.getRecipeId());
		setOperations.add(RECIPE_LIST_KEY + ":serving:" + recipe.getServing().toString(), id.getRecipeId());
		setOperations.add(RECIPE_LIST_KEY + ":level:" + recipe.getLevel().toString(), id.getRecipeId());
		setOperations.add(RECIPE_LIST_KEY + ":category:" + recipe.getCategory().toString(), id.getRecipeId());
		setOperations.add(RECIPE_KEY + recipe.getCooker().getEmail(), id.getRecipeId());

		saveRecipeHash(recipe, id, hashOperations);
	}

	private void saveRecipeHash(Recipe recipe, RecipeId id, HashOperations<String, Object, Object> hashOperations) {
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

			hashOperations.put(RECIPE_KEY + id, "cooker", recipe.getCooker().getEmail());
			hashOperations.put(RECIPE_KEY + id, "title", recipe.getTitle().getTitle());
			hashOperations.put(RECIPE_KEY + id, "mainImage", recipe.getMainImage().getPath());
			hashOperations.put(RECIPE_KEY + id, "category", recipe.getCategory().toString());
			hashOperations.put(RECIPE_KEY + id, "serving", recipe.getServing().toString());
			hashOperations.put(RECIPE_KEY + id, "level", recipe.getLevel().toString());
			hashOperations.put(RECIPE_KEY + id, "time", Long.toString(recipe.getTime().getTime()));
			hashOperations.put(RECIPE_KEY + id, "materials", objectMapper.writeValueAsString(recipe.getMaterials()));
			hashOperations.put(RECIPE_KEY + id, "makeProcess", objectMapper.writeValueAsString(recipe.getMakeProcess()));
			hashOperations.put(RECIPE_KEY + id, "createDateTime", simpleDateFormat.format(recipe.getCreateDateTime()));
		}catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException();
		}
	}

	@Override
	public Optional<Recipe> findByRecipeId(RecipeId targetId) {
		SetOperations setOperations = template.opsForSet();
		
		if(notExistRecipe(setOperations, targetId)) {
			return Optional.ofNullable(null);
		}
		
		String id = targetId.getRecipeId();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

		HashOperations hashOperations = template.opsForHash();
		try {
		Recipe recipe = Recipe.builder()
				.id(targetId)
				.cooker(new Cooker(hashOperations.get(RECIPE_KEY + id, "cooker").toString()))
				.title(new RecipeTitle(hashOperations.get(RECIPE_KEY + id, "title").toString()))
				.mainImage(new RecipeMainImage(hashOperations.get(RECIPE_KEY + id, "mainImage").toString()))
				.category(RecipeCategory.valueOf(hashOperations.get(RECIPE_KEY + id, "category").toString()))
				.serving(Serving.valueOf(hashOperations.get(RECIPE_KEY + id, "serving").toString()))
				.level(Level.valueOf(hashOperations.get(RECIPE_KEY + id, "level").toString()))
				.time(new MakingTime(Long.parseLong(hashOperations.get(RECIPE_KEY + id, "time").toString())))
				.materials(objectMapper.readValue(hashOperations.get(RECIPE_KEY + id, "materials").toString(), Materials.class))
				.makeProcess(objectMapper.readValue(hashOperations.get(RECIPE_KEY + id, "makeProcess").toString(), MakeProcesses.class))
				.createDateTime(simpleDateFormat.parse(hashOperations.get(RECIPE_KEY + id, "createDateTime").toString()))
				.build();
		return Optional.of(recipe);
		}catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException();
		}
	}

	private boolean notExistRecipe(SetOperations setOperations, RecipeId targetId) {
		Boolean existRecipeIntoKorean = setOperations.isMember(RECIPE_LIST_KEY + ":category:" + RecipeCategory.Korean.toString(), targetId.getRecipeId());
		Boolean existRecipeIntoChinese = setOperations.isMember(RECIPE_LIST_KEY + ":category:" + RecipeCategory.Chinese.toString(), targetId.getRecipeId());
		Boolean existRecipeIntoJapanese = setOperations.isMember(RECIPE_LIST_KEY + ":category:" + RecipeCategory.Japanese.toString(), targetId.getRecipeId());
		Boolean existRecipeIntoWestern = setOperations.isMember(RECIPE_LIST_KEY + ":category:" + RecipeCategory.Western.toString(), targetId.getRecipeId());
		
		return !existRecipeIntoKorean && !existRecipeIntoJapanese && !existRecipeIntoChinese && !existRecipeIntoWestern;
	}

	@Override
	public List<Recipe> findAll(RecipeSearch dto) {
		SetOperations setOperations = template.opsForSet();
		Collection otherKeys = new ArrayList<>();;
		if(dto.getWho() != null && !dto.getWho().isEmpty()) {
			otherKeys.add(RECIPE_KEY + dto.getWho());
		}
		if(dto.getLevel() != null) {
			otherKeys.add(RECIPE_LIST_KEY + ":level:" + dto.getLevel().toString());
		}
		if(dto.getServing() != null) {
			otherKeys.add(RECIPE_LIST_KEY + ":serving:" + dto.getServing().toString());
		}
		if(dto.getCategory() != null) {
			otherKeys.add(RECIPE_LIST_KEY + ":category:" + dto.getCategory().toString());
		}
		Set intersect = setOperations.intersect(RECIPE_LIST_KEY, otherKeys);
		List<Recipe> recipes = new ArrayList<>();
		intersect.forEach(key->{
			recipes.add(findByRecipeId(new RecipeId(key.toString())).get());
		});
		return recipes;
	}
}
