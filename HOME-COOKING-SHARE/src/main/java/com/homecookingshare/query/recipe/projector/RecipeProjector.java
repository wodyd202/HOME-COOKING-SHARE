package com.homecookingshare.query.recipe.projector;

import com.homecookingshare.domain.recipe.ChangedTitle;
import com.homecookingshare.domain.recipe.event.AddedMakeProcess;
import com.homecookingshare.domain.recipe.event.ChangedLevel;
import com.homecookingshare.domain.recipe.event.RegisteredRecipe;
import com.homecookingshare.domain.recipe.event.RemovedMakeProcess;

public interface RecipeProjector{
	void on(RegisteredRecipe event);
	
	void on(ChangedTitle event);
	
	void on(AddedMakeProcess event);
	
	void on(RemovedMakeProcess event);
	
	void on(ChangedLevel event);
}
