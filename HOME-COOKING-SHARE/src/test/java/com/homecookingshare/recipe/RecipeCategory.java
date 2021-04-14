package com.homecookingshare.recipe;

import lombok.AccessLevel;

import lombok.NoArgsConstructor;

import lombok.AllArgsConstructor;

import lombok.Getter;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecipeCategory {
	private String value;
}
