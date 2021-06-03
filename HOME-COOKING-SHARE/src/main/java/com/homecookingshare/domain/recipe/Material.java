package com.homecookingshare.domain.recipe;

import java.io.Serializable;

import com.homecookingshare.command.recipe.model.RecipeCommand.AddMaterial;
import com.homecookingshare.command.recipe.model.RecipeCommand.RemoveMaterial;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = { "name" })
public class Material implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private String capacity;
	
	public Material(AddMaterial material) {
		this.name = material.getName();
		this.capacity = material.getCapacity();
	}

	public Material(RemoveMaterial material) {
		this.name = material.getName();
		this.capacity = material.getCapacity();
	}
}
