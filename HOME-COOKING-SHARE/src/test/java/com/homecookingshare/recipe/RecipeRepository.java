package com.homecookingshare.recipe;

import java.util.List;

public interface RecipeRepository {

	List<RecipeListData> findAll(RecipeSearchDTO dto);

	void save(Recipe entity);

}
