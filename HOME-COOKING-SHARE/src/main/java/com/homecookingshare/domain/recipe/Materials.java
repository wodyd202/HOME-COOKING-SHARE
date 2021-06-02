package com.homecookingshare.domain.recipe;

import java.io.Serializable;
import java.util.List;

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
}
