package com.homecookingshare.recipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FakeRecipeRepository implements RecipeRepository {

	private final Map<String, List<Recipe>> repository = new HashMap<>();
	
	@Override
	public List<RecipeListData> findAll(RecipeSearchDTO dto) {
		List<RecipeListData> result = new ArrayList<>();
		List<Recipe> list = repository.get(dto.getWriter().toString());
		if(list == null) {
			list = new ArrayList<>();
		}
		list.forEach(c->{
			result.add(new RecipeListData());
		});
		return result;
	}

	@Override
	public void save(Recipe entity) {
		String email = entity.getWriter().toString();
		List<Recipe> list = repository.get(email);
		if(list == null) {
			repository.put(email, new ArrayList<>());
			list = repository.get(email);
		}
		list.add(entity);
	}

}
