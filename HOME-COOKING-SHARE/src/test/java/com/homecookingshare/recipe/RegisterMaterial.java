package com.homecookingshare.recipe;

import lombok.AccessLevel;

import lombok.NoArgsConstructor;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "material")
public class RegisterMaterial {
	private String material;
	private String capacity;
}
