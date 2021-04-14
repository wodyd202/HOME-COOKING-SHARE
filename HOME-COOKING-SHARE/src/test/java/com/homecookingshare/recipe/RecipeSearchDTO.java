package com.homecookingshare.recipe;

import com.homecookingshare.member.Email;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecipeSearchDTO {
	private Email writer;
}
