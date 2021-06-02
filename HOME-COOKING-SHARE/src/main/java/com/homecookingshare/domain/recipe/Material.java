package com.homecookingshare.domain.recipe;

import java.io.Serializable;

import com.homecookingshare.command.recipe.model.RecipeCommand.AddMaterial;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Material implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private String capacity;
	
	public Material(AddMaterial material) {
		this.name = material.getName();
		this.capacity = material.getCapacity();
	}
}
