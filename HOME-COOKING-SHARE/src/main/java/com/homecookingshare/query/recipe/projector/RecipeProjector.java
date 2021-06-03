package com.homecookingshare.query.recipe.projector;

import com.homecookingshare.domain.recipe.ChangedTitle;
import com.homecookingshare.domain.recipe.event.AddedMakeProcess;
import com.homecookingshare.domain.recipe.event.AddedMaterial;
import com.homecookingshare.domain.recipe.event.ChangedLevel;
import com.homecookingshare.domain.recipe.event.ChangedMainImage;
import com.homecookingshare.domain.recipe.event.ChangedServing;
import com.homecookingshare.domain.recipe.event.RegisteredRecipe;
import com.homecookingshare.domain.recipe.event.RemovedMakeProcess;
import com.homecookingshare.domain.recipe.event.RemovedMaterial;

public interface RecipeProjector{
	void on(final RegisteredRecipe event);
	
	void on(final ChangedTitle event);
	
	void on(final AddedMakeProcess event);
	
	void on(final RemovedMakeProcess event);
	
	void on(final ChangedLevel event);
	
	void on(final ChangedMainImage event);
	
	void on(final AddedMaterial event);
	
	void on(final RemovedMaterial event);
	
	void on(final ChangedServing event);
}
