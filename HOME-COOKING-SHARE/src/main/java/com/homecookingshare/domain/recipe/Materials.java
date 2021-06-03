package com.homecookingshare.domain.recipe;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Convert;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Materials implements Serializable{
	private static final long serialVersionUID = 1L;

	@Convert(converter = MaterialConverter.class)
	private List<Material> materials;

	public void add(Material material) {
		this.materials.add(material);
	}

	public void remove(Material material) {
		this.materials = materials.stream()
				.filter(materialValue->materialValue.getName().equals(materialValue.getName())).collect(Collectors.toList());
	}
}
