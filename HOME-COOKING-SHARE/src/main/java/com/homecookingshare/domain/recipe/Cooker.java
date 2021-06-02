package com.homecookingshare.domain.recipe;

import java.io.Serializable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "email")
public class Cooker implements Serializable {
	private static final long serialVersionUID = 1L;
	private String email;

	public boolean isMyRecipe(Recipe findRecipe) {
		return this.equals(findRecipe.getCooker());
	}

}
