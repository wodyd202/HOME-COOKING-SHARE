package com.homecookingshare.domain.recipe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Convert;

import com.homecookingshare.command.recipe.exception.InvalidRecipeException;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Materials implements Serializable {
	private static final long serialVersionUID = 1L;

	@Convert(converter = MaterialConverter.class)
	private List<Material> materials = new ArrayList<>();

	public void add(Material material) {
		this.materials.add(material);
	}

	public void remove(Material material) {
		for (int i = 0; i < materials.size(); i++) {
			if(materials.get(i).equals(material)) {
				this.materials.remove(i);
				return;
			}
		}
		throw new InvalidRecipeException("해당 레시피에 존재하지 않는 재료입니다.");
	}
}
